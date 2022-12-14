package org.mithwick.covid19.client.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mithwick.covid19.client.models.Covid19Information;
import org.mithwick.covid19.client.models.HistoricalDataStatus;
import org.mithwick.covid19.client.test.util.HTTPClientUtil;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import static java.net.HttpURLConnection.HTTP_FORBIDDEN;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mithwick.covid19.client.test.util.HTTPClientUtil.FORBIDDEN_ERROR_BODY;
import static org.mithwick.covid19.client.test.util.HTTPClientUtil.HISTORICAL_DATA_RESPONSE_BODY;
import static org.mithwick.covid19.client.test.util.HTTPClientUtil.LIVE_DATA_RESPONSE_BODY;
import static org.mithwick.covid19.client.test.util.HTTPClientUtil.VACCINE_DATA_RESPONSE_BODY;
import static org.mockito.ArgumentMatchers.eq;

class Covid19APIClientServiceTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private HttpClient mockHttpClient;

    ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockHttpClient = Mockito.mock(HttpClient.class);
        objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    public void getCurrentInformationURI_returnURI() {
        String country = "France";

        Covid19APIClientService covid19APIClientService = new Covid19APIClientService(objectMapper, mockHttpClient);
        URI currentInformationURI = covid19APIClientService.getCurrentInformationURI(country);

        assertEquals("/v1/cases", currentInformationURI.getPath());
        assertEquals("country=France", currentInformationURI.getQuery());
    }

    @Test
    public void getVaccineInformationURI_returnURI() {
        String country = "France";

        Covid19APIClientService covid19APIClientService = new Covid19APIClientService(objectMapper, mockHttpClient);
        URI vaccineInformationURI = covid19APIClientService.getVaccineInformationURI(country);

        assertEquals("/v1/vaccines", vaccineInformationURI.getPath());
        assertEquals("country=France", vaccineInformationURI.getQuery());
    }

    @Test
    public void getHistoricalInformationURI_returnURI() {
        String country = "France";

        Covid19APIClientService covid19APIClientService = new Covid19APIClientService(objectMapper, mockHttpClient);
        URI historicalInformationURI = covid19APIClientService.getHistoricalInformationURI(country, HistoricalDataStatus.CONFIRMED);

        assertEquals("/v1/history", historicalInformationURI.getPath());
        assertEquals("status=confirmed&country=France", historicalInformationURI.getQuery());
    }

    @Test
    public void getCovid19Information_allCallsSuccess_ReturnCorrectInformation() throws IOException, InterruptedException {
        String country = "France";
        long expectedConfirmedCases = 2604595;
        long expectedRecoveredCases = 195365;
        long expectedDeaths = 62548;
        long expectedTotalPopulation = 64979548;
        long expectedCVaccinatedCount = 2297100;

        int statusCode = HTTP_OK;
        HttpResponse<String> liveDataResponse = HTTPClientUtil.getMockHttpResponse(statusCode, LIVE_DATA_RESPONSE_BODY);
        HttpResponse<String> historicalDataResponse = HTTPClientUtil.getMockHttpResponse(statusCode, HISTORICAL_DATA_RESPONSE_BODY);
        HttpResponse<String> vaccineDataResponse = HTTPClientUtil.getMockHttpResponse(statusCode, VACCINE_DATA_RESPONSE_BODY);

        Mockito.when(mockHttpClient.send(Mockito.any(), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(liveDataResponse)
                .thenReturn(historicalDataResponse)
                .thenReturn(vaccineDataResponse);

        Covid19APIClientService covid19APIClientService = new Covid19APIClientService(objectMapper, mockHttpClient);
        Covid19Information covid19Information = covid19APIClientService.getCovid19Information(country);

        assertNotNull(covid19Information);
        assertNotNull(covid19Information.getLiveData());
        assertEquals(expectedConfirmedCases, covid19Information.getLiveData().getConfirmed());
        assertEquals(expectedRecoveredCases, covid19Information.getLiveData().getRecovered());
        assertEquals(expectedDeaths, covid19Information.getLiveData().getDeaths());

        assertNotNull(covid19Information.getHistoricalData());
        assertNotNull(covid19Information.getHistoricalData().getDates());
        assertFalse(covid19Information.getHistoricalData().getDates().isEmpty());

        assertNotNull(covid19Information.getVaccineData());
        assertEquals(expectedTotalPopulation, covid19Information.getVaccineData().getPopulation());
        assertEquals(expectedCVaccinatedCount, covid19Information.getVaccineData().getPeopleVaccinated());

    }

    @Test
    public void getCovid19Information_allCallsFailIO_ReturnNoInformation() throws IOException, InterruptedException {
        String country = "France";

        Mockito.when(mockHttpClient.send(Mockito.any(), eq(HttpResponse.BodyHandlers.ofString())))
                .thenThrow(new IOException());

        Covid19APIClientService covid19APIClientService = new Covid19APIClientService(objectMapper, mockHttpClient);
        Covid19Information covid19Information = covid19APIClientService.getCovid19Information(country);

        assertNotNull(covid19Information);
        assertNull(covid19Information.getLiveData());
        assertNull(covid19Information.getHistoricalData());
        assertNull(covid19Information.getVaccineData());
    }

    @Test
    public void getCovid19Information_notOkResponse_ReturnNoInformation() throws IOException, InterruptedException {
        String country = "France";

        int statusCode = HTTP_FORBIDDEN;
        HttpResponse<String> liveDataResponse = HTTPClientUtil.getMockHttpResponse(statusCode, FORBIDDEN_ERROR_BODY);
        HttpResponse<String> historicalDataResponse = HTTPClientUtil.getMockHttpResponse(statusCode, FORBIDDEN_ERROR_BODY);
        HttpResponse<String> vaccineDataResponse = HTTPClientUtil.getMockHttpResponse(statusCode, FORBIDDEN_ERROR_BODY);

        Mockito.when(mockHttpClient.send(Mockito.any(), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(liveDataResponse)
                .thenReturn(historicalDataResponse)
                .thenReturn(vaccineDataResponse);

        Covid19APIClientService covid19APIClientService = new Covid19APIClientService(objectMapper, mockHttpClient);
        Covid19Information covid19Information = covid19APIClientService.getCovid19Information(country);

        assertNotNull(covid19Information);
        assertNull(covid19Information.getLiveData());
        assertNull(covid19Information.getHistoricalData());
        assertNull(covid19Information.getVaccineData());
    }
}