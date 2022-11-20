package org.mithwick.covid19.client.services.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mithwick.covid19.client.models.response.Covid19APIResponse;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Objects;

public class Covid19APIUtil {
    private static final String BASE_URL = "https://covid-api.mmediagroup.fr/v1";
    private static final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private final HttpClient httpClient;
    private String country;

    public Covid19APIUtil(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void setCountry(String country) {
        this.country = URLEncoder.encode(Objects.requireNonNull(country), StandardCharsets.UTF_8);
    }

    public String setCountry() {
        return country;
    }

    /**
     * Make GET request to Covide-19 API
     *
     * @param uri          url to get information
     * @param contentClass type of response expected
     * @return Covid 19 API response
     */
    public <T> Covid19APIResponse<T> doGetRequest(URI uri, Class<T> contentClass) {
        System.out.print("\tFetching ".concat(contentClass.getSimpleName()).concat(" information"));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .timeout(Duration.ofSeconds(60))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        JavaType type = mapper.getTypeFactory().constructParametricType(Covid19APIResponse.class, contentClass);

        try {
            HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            Covid19APIResponse<T> covid19APIResponse = mapper.readValue(response.body(), type);

            System.out.println(" - done");
            return covid19APIResponse;
        } catch (IOException | InterruptedException e) {
            System.err.println("\n\tAn error occurred: " + e.getMessage());
            return null;
        }
    }

    /**
     * Get current information URL
     *
     * @return current information URL
     */
    public URI getCurrentInformationURI() {
        return URI.create(BASE_URL.concat("/cases?country=").concat(country));
    }

    /**
     * Get vaccine information URL
     *
     * @return vaccine information URL
     */
    public URI getVaccineInformationURI() {
        return URI.create(BASE_URL.concat("/vaccines?country=").concat(country));
    }

    /**
     * Get historical confirmed cases information URL
     *
     * @return historical confirmed cases information URL
     */
    public URI getHistoricalInformationURI() {
        return URI.create(BASE_URL.concat("/history?status=confirmed&country=").concat(country));
    }

}
