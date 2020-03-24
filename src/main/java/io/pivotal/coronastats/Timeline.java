package io.pivotal.coronastats;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Timeline {
    @JsonProperty("confirmed")
    private History confirmed;

    @JsonProperty("deaths")
    private History deaths;

    @JsonProperty("recovered")
    private History recovered;
}
