package io.pivotal.coronastats;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.FileReader;
import java.io.IOException;

@SpringBootApplication
@EnableCaching
@EnableCircuitBreaker
@EnableSwagger2
public class CoronoStatsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoronoStatsApplication.class, args);
    }

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        CloseableHttpClient httpClient = HttpClients.custom()
          .setSSLHostnameVerifier(new NoopHostnameVerifier())
          .build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        return builder.requestFactory(() -> requestFactory).build();
    }

    @Bean
    public Docket api() throws IOException, XmlPullParserException {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = reader.read(new FileReader("pom.xml"));
        ApiInfoBuilder builder = new ApiInfoBuilder()
          .title("Corona Stats API Specification")
          .description("Documentation automatically generated")
          .version(model.getVersion())
          .contact(new Contact("Ravi Panchal", "pivotal.io", "rpanchal@pivotal.io"));
        return new Docket(DocumentationType.SWAGGER_2).select()
          .apis(RequestHandlerSelectors.basePackage("io.pivotal.coronastats"))
          .paths(PathSelectors.any()).build()
          .apiInfo(builder.build());
    }
}
