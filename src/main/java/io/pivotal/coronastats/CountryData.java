package io.pivotal.coronastats;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
public class CountryData {
    @JsonProperty("total_cases")
    private Long total_cases;
    @JsonProperty("total_recovered")
    private Long totalRecovered;
    @JsonProperty("total_unresolved")
    private Long totalUnresolved;
    @JsonProperty("total_deaths")
    private Long totalDeaths;
    @JsonProperty("total_new_cases_today")
    private Long totalNewCasesToday;
    @JsonProperty("total_new_deaths_today")
    private Long totalNewDeathsToday;
    @JsonProperty("total_active_cases")
    private Long totalActiveCases;
    @JsonProperty("total_serious_cases")
    private Long totalSeriousCases;
}
