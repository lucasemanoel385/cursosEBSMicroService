package br.com.cursosEBS.courses.service;

import br.com.cursosEBS.courses.dto.CreatePlaylistGumletDTO;
import br.com.cursosEBS.courses.dto.GumletUploadResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GumletService {

    @Value("${gumlet.api.key}")
    private String apiKey;

    @Value("${gumlet.upload.direct.endpoint}")
    private String uploadEndpoint;

    @Value("${gumlet.create.playlist.endpoint}")
    private String createPlaylistEndpoint;

    @Value("${gumlet.collectionid}")
    private String colleactionId;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public GumletService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    // Etapa 1 - Cria o asset e obtém o upload_url
    public GumletUploadResponse createUploadAsset(String title, String playlistId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> payload = new HashMap<>();
        payload.put("collection_id", colleactionId);
        payload.put("format", "hls");
        payload.put("title", title);
        payload.put("playlist_id", playlistId);
        payload.put("resolution", List.of("240p", "360p", "720p", "1080p"));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(uploadEndpoint, request, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            // Verifica o conteúdo da resposta antes de mapear
            try {
                String responseBody = response.getBody();
                if (responseBody == null || responseBody.isEmpty()) {
                    throw new RuntimeException("Resposta vazia da API Gumlet");
                }

                return objectMapper.readValue(responseBody, GumletUploadResponse.class);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao processar resposta da Gumlet", e);
            }
        } else {
            throw new RuntimeException("Erro ao criar asset no Gumlet: " + response.getBody());
        }
    }

    // Etapa 2 - Faz o PUT do vídeo para o upload_url
    public void uploadVideoToSignedUrl(String uploadUrl, MultipartFile file) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        HttpEntity<byte[]> request = new HttpEntity<>(file.getBytes(), headers);
        try {
            // Garantindo que a URL seja tratada corretamente e nao mude com o restTemplate
            URI uri = new URI(uploadUrl);

            // Fazendo a requisição PUT para a URL com os parâmetros corretamente preservados
            ResponseEntity<String> response = restTemplate.exchange(
                    uri,
                    HttpMethod.PUT,
                    request,
                    String.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Upload bem-sucedido!");
            } else {
                throw new RuntimeException("Erro no upload para Gumlet S3: " + response.getStatusCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao fazer upload do vídeo: " + e.getMessage());
        }
    }

    // Etapa 1 - Cria o asset e obtém o upload_url
    public CreatePlaylistGumletDTO createPlaylist(String title) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> payload = new HashMap<>();
        payload.put("collection_id", colleactionId);
        payload.put("title", title);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(createPlaylistEndpoint, request, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            // Verifica o conteúdo da resposta antes de mapear
            try {
                String responseBody = response.getBody();
                if (responseBody == null || responseBody.isEmpty()) {
                    throw new RuntimeException("Resposta vazia da API Gumlet");
                }

                return objectMapper.readValue(responseBody, CreatePlaylistGumletDTO.class);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao processar resposta da Gumlet", e);
            }
        } else {
            throw new RuntimeException("Erro ao criar asset no Gumlet: " + response.getBody());
        }
    }
}
