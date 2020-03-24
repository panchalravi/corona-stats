package io.pivotal.coronastats;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class History {
    @JsonProperty("latest")
    private Long latest;
    @JsonProperty("timeline")
    private Map<String, Long> timeline;
}
