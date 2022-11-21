package org.mithwick.covid19.client.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mithwick.covid19.client.Constants;
import org.mithwick.covid19.client.models.Covid19Information;
import org.mithwick.covid19.client.models.HistoricalData;
import org.mithwick.covid19.client.models.LiveData;
import org.mithwick.covid19.client.models.VaccineData;
import org.mithwick.covid19.client.models.response.Covid19APIResponse;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Covid19APIClientService {
    private static final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private final HttpClient httpClient;

    public Covid19APIClientService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Get current information URL
     *
     * @return current information URL
     */
    public URI getCurrentInformationURI(String country) {
        return URI.create(Constants.BASE_URL.concat(Constants.CURRENT_DATA_PATH).concat(country));
    }

    /**
     * Get historical confirmed cases information URL
     *
     * @return historical confirmed cases information URL
     */
    public URI getHistoricalInformationURI(String country) {
        return URI.create(Constants.BASE_URL.concat(Constants.HISTORY_DATA_PATH).concat(country));
    }

    /**
     * Get vaccine information URL
     *
     * @return vaccine information URL
     */
    public URI getVaccineInformationURI(String country) {
        return URI.create(Constants.BASE_URL.concat(Constants.VACCINE_DATA_PATH).concat(country));
    }

    /**
     * Fetch  Covid-19 information
     *
     * @param country name of the country to fetch information
     * @return Covid-19 information
     */
    public Covid19Information getCovid19Information(String country) {
        // encode the country string to correctly send all valid characters
        String encodedCountry = URLEncoder.encode(Objects.requireNonNull(country), StandardCharsets.UTF_8);

        Covid19APIResponse<LiveData> liveDataResponse = doGetRequest(getCurrentInformationURI(encodedCountry), LiveData.class);
        Covid19APIResponse<HistoricalData> historicalDataResponse = doGetRequest(getHistoricalInformationURI(encodedCountry), HistoricalData.class);
        Covid19APIResponse<VaccineData> vaccineDataResponse = doGetRequest(getVaccineInformationURI(encodedCountry), VaccineData.class);

        Covid19Information covid19Information = new Covid19Information(country);

        if (liveDataResponse != null) {
            covid19Information.setLiveData(liveDataResponse.getData());
        }

        if (historicalDataResponse != null) {
            covid19Information.setHistoricalData(historicalDataResponse.getData());
        }

        if (vaccineDataResponse != null) {
            covid19Information.setVaccineData(vaccineDataResponse.getData());
        }

        return covid19Information;
    }

    /**
     * Make GET request to Covid-19 API
     *
     * @param uri          url to get information
     * @param contentClass type of response expected
     * @return Covid 19 API response
     */
    public <T> Covid19APIResponse<T> doGetRequest(URI uri, Class<T> contentClass) {
        System.out.print(Constants.FETCHING_MESSAGE_PREFIX.concat(contentClass.getSimpleName()).concat(Constants.FETCHING_MESSAGE_PREFIX_SUFFIX));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header(Constants.CONTENT_TYPE_KEY, Constants.CONTENT_TYPE_JSON)
                .GET()
                .build();
        JavaType type = mapper.getTypeFactory().constructParametricType(Covid19APIResponse.class, contentClass);

        try {
            HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            Covid19APIResponse<T> covid19APIResponse = mapper.readValue(response.body(), type);

            // todo check response code
            System.out.println(Constants.FETCHING_MESSAGE_END);
            return covid19APIResponse;
        } catch (IOException | InterruptedException e) {
            // do not fail the flow if we do not get a response
            System.err.println(Constants.FETCHING_MESSAGE_ERROR + e.getMessage());
            return null;
        }
    }
}
