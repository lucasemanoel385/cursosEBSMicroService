package br.com.cursosEBS.courses.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GumletUploadResponse {

    @JsonProperty("asset_id")
    private String assetId;

    @JsonProperty("upload_url")
    private String upload_url;

    @JsonProperty("output")
    private Output output;

    public String getAssetId() {
        return assetId;
    }

    public String getUpload_url() {
        return upload_url;
    }

    public Output getOutput() {
        return output;
    }

    public static class Output {
        @JsonProperty("playback_url")
        private String playback_url;

        public String getPlayback_url() {
            return playback_url;
        }
    }
}
