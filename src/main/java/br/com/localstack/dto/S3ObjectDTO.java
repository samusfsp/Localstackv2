package br.com.localstack.dto;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class S3ObjectDTO {
    @JsonProperty("key")
    private String key;

    @JsonProperty("lastModified")
    private String lastModified;

    public S3ObjectDTO(String key, String lastModified) {
        this.key = key;
        this.lastModified = lastModified;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }
}
