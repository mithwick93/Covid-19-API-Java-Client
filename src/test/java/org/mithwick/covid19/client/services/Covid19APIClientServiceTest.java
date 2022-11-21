package org.mithwick.covid19.client.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mithwick.covid19.client.models.Covid19Information;
import org.mithwick.covid19.client.test.util.HTTPClientUtil;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mithwick.covid19.client.test.util.HTTPClientUtil.HISTORICAL_DATA_RESPONSE_BODY;
import static org.mithwick.covid19.client.test.util.HTTPClientUtil.LIVE_DATA_RESPONSE_BODY;
import static org.mithwick.covid19.client.test.util.HTTPClientUtil.VACCINE_DATA_RESPONSE_BODY;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class Covid19APIClientServiceTest {
    private final PrintStream standardOut = System.out;
    private final PrintStream errOut = System.err;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private HttpClient mockHttpClient;

    @BeforeEach
    public void setUp() {
        mockHttpClient = Mockito.mock(HttpClient.class);
        System.setOut(new PrintStream(outputStreamCaptor));
        System.setErr(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
        System.setErr(errOut);
    }

    @Test
    public void getCurrentInformationURI_returnURI() {
        String country = "France";

        Covid19APIClientService covid19APIClientService = new Covid19APIClientService(mockHttpClient);
        URI currentInformationURI = covid19APIClientService.getCurrentInformationURI(country);

        assertEquals("/v1/cases", currentInformationURI.getPath());
        assertEquals("country=France", currentInformationURI.getQuery());
    }

    @Test
    public void getVaccineInformationURI_returnURI() {
        String country = "France";

        Covid19APIClientService covid19APIClientService = new Covid19APIClientService(mockHttpClient);
        URI vaccineInformationURI = covid19APIClientService.getVaccineInformationURI(country);

        assertEquals("/v1/vaccines", vaccineInformationURI.getPath());
        assertEquals("country=France", vaccineInformationURI.getQuery());
    }

    @Test
    public void getHistoricalInformationURI_returnURI() {
        String country = "France";

        Covid19APIClientService covid19APIClientService = new Covid19APIClientService(mockHttpClient);
        URI historicalInformationURI = covid19APIClientService.getHistoricalInformationURI(country);

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

        int statusCode = 200;
        HttpResponse<String> liveDataResponse = HTTPClientUtil.getMockHttpResponse(statusCode, LIVE_DATA_RESPONSE_BODY);
        HttpResponse<String> historicalDataResponse = HTTPClientUtil.getMockHttpResponse(statusCode, HISTORICAL_DATA_RESPONSE_BODY);
        HttpResponse<String> vaccineDataResponse = HTTPClientUtil.getMockHttpResponse(statusCode, VACCINE_DATA_RESPONSE_BODY);

        when(mockHttpClient.send(Mockito.any(), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(liveDataResponse)
                .thenReturn(historicalDataResponse)
                .thenReturn(vaccineDataResponse);

        Covid19APIClientService covid19APIClientService = new Covid19APIClientService(mockHttpClient);
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

        when(mockHttpClient.send(Mockito.any(), eq(HttpResponse.BodyHandlers.ofString())))
                .thenThrow(new IOException());

        Covid19APIClientService covid19APIClientService = new Covid19APIClientService(mockHttpClient);
        Covid19Information covid19Information = covid19APIClientService.getCovid19Information(country);

        assertNotNull(covid19Information);
        assertNull(covid19Information.getLiveData());
        assertNull(covid19Information.getHistoricalData());
        assertNull(covid19Information.getVaccineData());

    }

}