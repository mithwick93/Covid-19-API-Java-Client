package org.mithwick.covid19.client.test.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.net.ssl.SSLSession;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HTTPClientUtil {

    public static final String LIVE_DATA_RESPONSE_BODY = "{\r\n  \"All\": {\r\n    \"confirmed\": 2604595,\r\n    \"recovered\": 195365,\r\n    \"deaths\": 62548,\r\n    \"country\": \"France\",\r\n    \"population\": 64979548,\r\n    \"sq_km_area\": 551500,\r\n    \"life_expectancy\": \"78.8\",\r\n    \"elevation_in_meters\": 375,\r\n    \"continent\": \"Europe\",\r\n    \"abbreviation\": \"FR\",\r\n    \"location\": \"Western Europe\",\r\n    \"iso\": 250,\r\n    \"capital_city\": \"Paris\",\r\n    \"lat\": \"46.2276\",\r\n    \"long\": \"2.2137\",\r\n    \"updated\": \"2020/12/26 12:21:56+00\"\r\n  },\r\n  \"French Guiana\": {\r\n    \"lat\": \"4\",\r\n    \"long\": \"-53\",\r\n    \"confirmed\": 12685,\r\n    \"recovered\": 9995,\r\n    \"deaths\": 71,\r\n    \"updated\": \"2020/12/26 12:21:56+00\"\r\n  }\r\n}";

    public static final String HISTORICAL_DATA_RESPONSE_BODY = "{\r\n  \"All\": {\r\n    \"country\": \"France\",\r\n    \"population\": 64979548,\r\n    \"sq_km_area\": 551500,\r\n    \"life_expectancy\": \"78.8\",\r\n    \"elevation_in_meters\": 375,\r\n    \"continent\": \"Europe\",\r\n    \"abbreviation\": \"FR\",\r\n    \"location\": \"Western Europe\",\r\n    \"iso\": 250,\r\n    \"capital_city\": \"Paris\",\r\n    \"dates\": {\r\n      \"2020-12-25\": 2604495,\r\n      \"2020-12-24\": 2604395,\r\n      \"2020-12-23\": 2604295,\r\n      \"2020-12-22\": 2604195,\r\n      \"2020-12-21\": 2604095\r\n    }\r\n  }\r\n}";

    public static final String VACCINE_DATA_RESPONSE_BODY = "{\r\n  \"All\": {\r\n    \"administered\": 7927771,\r\n    \"people_vaccinated\": 2297100,\r\n    \"people_partially_vaccinated\": 5630671,\r\n    \"country\": \"France\",\r\n    \"population\": 64979548,\r\n    \"sq_km_area\": 551500,\r\n    \"life_expectancy\": \"78.8\",\r\n    \"elevation_in_meters\": 375,\r\n    \"continent\": \"Europe\",\r\n    \"abbreviation\": \"FR\",\r\n    \"location\": \"Western Europe\",\r\n    \"iso\": 250,\r\n    \"capital_city\": \"Paris\",\r\n    \"lat\": \"46.2276\",\r\n    \"long\": \"2.2137\",\r\n    \"updated\": \"2020/12/26 12:21:56+00\"\r\n  }\r\n}";

    public static final String FORBIDDEN_ERROR_BODY = "{\"message\":\"Missing Authentication Token\"}";

    /**
     * Get Mock Http response
     *
     * @param statusCode http status code
     * @param body       json string
     * @return Mocked Http Response
     */
    public static HttpResponse<String> getMockHttpResponse(final int statusCode, final String body) {
        return new HttpResponse<>() {
            @Override
            public int statusCode() {
                return statusCode;
            }

            @Override
            public HttpRequest request() {
                return null;
            }

            @Override
            public Optional<HttpResponse<String>> previousResponse() {
                return Optional.empty();
            }

            @Override
            public HttpHeaders headers() {
                return null;
            }

            @Override
            public String body() {
                return body;
            }

            @Override
            public Optional<SSLSession> sslSession() {
                return Optional.empty();
            }

            @Override
            public URI uri() {
                return null;
            }

            @Override
            public HttpClient.Version version() {
                return null;
            }
        };
    }
}
