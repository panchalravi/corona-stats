package io.pivotal.coronastats;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Location {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("coordinates")
    private Coordinates coordinates;

    @JsonProperty("country")
    private String country;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("latest")
    private Latest latest;

    @JsonProperty("last_updated")
    private String lastUpdated;

    @JsonProperty("province")
    private String province;

    @JsonProperty("timelines")
    private Timeline timeline;
}
