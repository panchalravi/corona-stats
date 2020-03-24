package io.pivotal.coronastats;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class LocationsDataWrapper {
    @JsonProperty("latest")
    private Latest latest;

    @JsonProperty("locations")
    private List<Location> location;
}
