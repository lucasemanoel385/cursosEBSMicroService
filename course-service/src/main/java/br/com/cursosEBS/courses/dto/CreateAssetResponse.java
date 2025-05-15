package br.com.cursosEBS.courses.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateAssetResponse {

    @JsonProperty("upload_url")
    private String uploadUrl;

    @JsonProperty("asset_id")
    private String assetId;

    public String getUploadUrl() {
        return uploadUrl;
    }

    public String getAssetId() {
        return assetId;
    }
}
