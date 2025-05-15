package br.com.cursosEBS.courses.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreatePlaylistGumletDTO {

    @JsonProperty("id")
    private String id;

    @JsonProperty("asset_id")
    private String assetId;

    public String getId() {
        return id;
    }

}
