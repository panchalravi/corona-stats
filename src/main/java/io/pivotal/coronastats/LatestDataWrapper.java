package io.pivotal.coronastats;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LatestDataWrapper {
    @JsonProperty("latest")
    private Latest latest;
}

