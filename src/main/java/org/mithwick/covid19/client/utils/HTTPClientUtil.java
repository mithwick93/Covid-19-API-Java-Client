package org.mithwick.covid19.client.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mithwick.covid19.client.models.response.HistoricalCasesResponse;
import org.mithwick.covid19.client.models.response.LiveCasesResponse;
import org.mithwick.covid19.client.models.response.VaccinesResponse;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

public class HTTPClientUtil {

    private static final String BASE_URL = "https://covid-api.mmediagroup.fr/v1";
    private final HttpClient httpClient;
    private final String encodedCountry;

    private final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public HTTPClientUtil(HttpClient httpClient, String country) {
        this.httpClient = httpClient;
        this.encodedCountry = URLEncoder.encode(country, StandardCharsets.UTF_8);
    }

    public LiveCasesResponse getCurrentInformation() {
        System.out.print("Fetching current information");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(getCurrentInformationURI())
                .timeout(Duration.of(60, SECONDS))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            LiveCasesResponse liveCasesResponse = mapper.readValue(response.body(), LiveCasesResponse.class);

            System.out.println(" - done");
            return liveCasesResponse;
        } catch (IOException | InterruptedException e) {
            System.err.println("An error occurred: " + e.getMessage());
            return null;
        }
    }

    public VaccinesResponse getVaccineInformation() {
        System.out.print("Fetching vaccine information");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(getVaccineInformationURI())
                .timeout(Duration.of(60, SECONDS))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            VaccinesResponse vaccinesResponse = mapper.readValue(response.body(), VaccinesResponse.class);

            System.out.println(" - done");
            return vaccinesResponse;
        } catch (IOException | InterruptedException e) {
            System.err.println("An error occurred: " + e.getMessage());
            return null;
        }
    }

    public HistoricalCasesResponse getHistoricalInformation() {
        System.out.print("Fetching historical information");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(getHistoricalInformationURI())
                .timeout(Duration.of(60, SECONDS))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            HistoricalCasesResponse historicalCasesResponse = mapper.readValue(response.body(), HistoricalCasesResponse.class);

            System.out.println(" - done");
            return historicalCasesResponse;
        } catch (IOException | InterruptedException e) {
            System.err.println("An error occurred: " + e.getMessage());
            return null;
        }
    }

    private URI getCurrentInformationURI() {
        return URI.create(BASE_URL.concat("/cases?country=").concat(encodedCountry));
    }

    private URI getVaccineInformationURI() {
        return URI.create(BASE_URL.concat("/vaccines?country=").concat(encodedCountry));
    }

    private URI getHistoricalInformationURI() {
        return URI.create(BASE_URL.concat("/history?status=confirmed&country=").concat(encodedCountry));
    }

}
