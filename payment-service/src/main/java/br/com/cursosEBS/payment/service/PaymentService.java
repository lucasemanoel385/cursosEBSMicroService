package br.com.cursosEBS.payment.service;

import br.com.cursosEBS.payment.dto.PreferenceDTO;
import br.com.cursosEBS.payment.entity.Payment;
import br.com.cursosEBS.payment.entity.StatusPayment;
import br.com.cursosEBS.payment.http.SubscriptionClient;
import br.com.cursosEBS.payment.http.dto.RegisterSubscriptionDTO;
import br.com.cursosEBS.payment.repository.PaymentRepository;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private SubscriptionClient subscriptionClient;

    @Value("${url.payment.success}")
    private String paymentSuccess;

    @Value("${url.payment.pending}")
    private String paymentPending;

    @Value("${url.payment.failure}")
    private String paymentFailure;

    public PreferenceDTO processPayment(String id) throws MPApiException, MPException {

        // Criar item de pagamento
        PreferenceItemRequest item = PreferenceItemRequest.builder()
                .title("Matricula CursosEBS")
                .quantity(1)
                .unitPrice(BigDecimal.valueOf(200.0f))
                .currencyId("BRL")
                .build();

        // URLs de retorno
        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success(paymentSuccess)
                .failure(paymentFailure)
                .pending(paymentPending)
                .build();

        // Criar preferência
        PreferenceRequest preferencia = PreferenceRequest.builder()
                .items(Collections.singletonList(item))
                .backUrls(backUrls)
                .autoReturn("approved")
                .externalReference(id)
                .build();

        // Enviar preferência para o Mercado Pago
        PreferenceClient client = new PreferenceClient();
        Preference resposta;

        try {
            resposta = client.create(preferencia);
        } catch (MPApiException e) {
            System.out.println("Erro da API Mercado Pago:");
            System.out.println("Status: " + e.getStatusCode());
            System.out.println("Mensagem: " + e.getApiResponse().getContent());
            throw e;
        }

        return new PreferenceDTO(resposta.getId(), resposta.getInitPoint());
    }
}
