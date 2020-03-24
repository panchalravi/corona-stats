package io.pivotal.coronastats;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotBlank;

@RestController
@Log4j2
public class CoronaStatsRestController {
    private RestTemplate restTemplate;
    private String virusTrackerBaseUrl;

    public CoronaStatsRestController(RestTemplate restTemplate, @Value("${virustracker.baseurl}") String virusTrackerBaseUrl) {
        this.restTemplate = restTemplate;
        this.virusTrackerBaseUrl = virusTrackerBaseUrl;

    }
    /*
    @GetMapping("/country/{code}")
    public ResponseEntity<CountryStats> countryStats(@PathVariable("code") String code) {
        String countryStatsUrl = String.format(this.virusTrackerBaseUrl + "?countryTotal=%s", code);
        log.info("Invoking external API: {}", countryStatsUrl);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.ACCEPT_ENCODING, "application/json");
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(countryStatsUrl, HttpMethod.GET, httpEntity, CountryStats.class);
    }
    */

    @GetMapping("/latest")
    @Cacheable(value = "latest", unless="#result==null")
    @HystrixCommand(fallbackMethod = "defaultLatest")
    public LatestDataWrapper latest() {
        String url = this.virusTrackerBaseUrl + "/latest";
        log.info("Invoking external API: {}", url);
        return restTemplate.getForObject(url, LatestDataWrapper.class);
    }

    @GetMapping("/locations")
    @Cacheable(value = "locations", unless="#result==null")
    @HystrixCommand(fallbackMethod = "defaultLocations", commandProperties = {
      @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="5000")
    })
    public LocationsDataWrapper locations() {
        String url = this.virusTrackerBaseUrl + "/locations";
        log.info("Invoking external API: {}", url);
        return restTemplate.getForObject(url, LocationsDataWrapper.class);
    }

    @RequestMapping(path = "/locations/{countryCode}", method = RequestMethod.GET)
    @Cacheable(value = "locations", unless="#result==null")
    @HystrixCommand(fallbackMethod = "defaultLocations")
    public LocationsDataWrapper locations(@RequestParam(value = "timeline", required = false) String timeline,
                                          @NotBlank @PathVariable("countryCode") String countryCode) {
        String url = String.format(this.virusTrackerBaseUrl + "/locations?country_code=%s", countryCode);
        if (timeline != null) {
            url += "&timelines=1";
        }
        log.info("Invoking external API: {}", url);
        return restTemplate.getForObject(url, LocationsDataWrapper.class);
    }

    //TODO Read data from cache
    public LocationsDataWrapper defaultLocations(String timeline, String countryCode) {
        log.info("Invoking fallback for /locations api");
        return new LocationsDataWrapper();
    }

    //TODO Read data from cache
    public LocationsDataWrapper defaultLocations() {
        log.info("Invoking fallback for /locations api");
        return new LocationsDataWrapper();
    }

    //TODO Read data from cache
    public LatestDataWrapper defaultLatest() {
        log.info("Invoking fallback for /latest api");
        return new LatestDataWrapper();
    }
}
