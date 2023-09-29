package br.com.localstack.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

public class BucketDTO {
    @JsonProperty("name")
    private String name;

    public BucketDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
