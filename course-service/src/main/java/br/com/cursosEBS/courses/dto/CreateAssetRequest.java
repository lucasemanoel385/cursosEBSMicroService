package br.com.cursosEBS.courses.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CreateAssetRequest {
    @JsonProperty("source_id")
    private String sourceId;

    private String format = "hls";

    private List<String> resolution = List.of("240p", "360p", "720p", "1080p");

    @JsonProperty("keep_original")
    private boolean keepOriginal = true;

    public CreateAssetRequest(String sourceId) {
        this.sourceId = sourceId;
    }

}
