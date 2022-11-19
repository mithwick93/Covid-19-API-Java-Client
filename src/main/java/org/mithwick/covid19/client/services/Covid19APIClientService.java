package org.mithwick.covid19.client.services;

import org.mithwick.covid19.client.models.HistoricalCases;
import org.mithwick.covid19.client.models.LiveCases;
import org.mithwick.covid19.client.models.Vaccines;
import org.mithwick.covid19.client.models.response.HistoricalCasesResponse;
import org.mithwick.covid19.client.models.response.LiveCasesResponse;
import org.mithwick.covid19.client.models.response.VaccinesResponse;
import org.mithwick.covid19.client.utils.HTTPClientUtil;

import java.net.http.HttpClient;

public class Covid19APIClientService {

    private static final String NA = "N/A";
    private final HTTPClientUtil httpClientUtil;
    private final String country;

    public Covid19APIClientService(HttpClient httpClient, String country) {
        this.httpClientUtil = new HTTPClientUtil(httpClient, country);
        this.country = country;
    }

    public void displayInformation() {
        LiveCasesResponse currentLiveCasesResponseInformation = httpClientUtil.getCurrentInformation();
        VaccinesResponse vaccinesResponse = httpClientUtil.getVaccineInformation();
        HistoricalCasesResponse historicalCasesResponse = httpClientUtil.getHistoricalInformation();

        System.out.println("Covid-19 Information of ".concat(country));
        displayLiveCases(currentLiveCasesResponseInformation);
        displayVaccines(vaccinesResponse);
        displayHistorical(currentLiveCasesResponseInformation, historicalCasesResponse);
    }

    public void displayLiveCases(LiveCasesResponse currentLiveCasesResponseInformation) {
        String confirmed = NA;
        String recovered = NA;
        String deaths = NA;

        if (currentLiveCasesResponseInformation != null) {
            LiveCases liveCases = currentLiveCasesResponseInformation.getAll();

            confirmed = String.format("%,d", liveCases.getConfirmed());
            recovered = String.format("%,d", liveCases.getRecovered());
            deaths = String.format("%,d", liveCases.getDeaths());
        }

        System.out.println("\tConfirmed: ".concat(confirmed));
        System.out.println("\tRecovered: ".concat(recovered));
        System.out.println("\tDeaths: ".concat(deaths));
    }

    public void displayVaccines(VaccinesResponse vaccinesResponse) {
        if (vaccinesResponse != null && vaccinesResponse.getAll() != null) {
            Vaccines vaccines = vaccinesResponse.getAll();
            Long population = vaccines.getPopulation();
            Long peopleVaccinated = vaccines.getPeopleVaccinated();

            if (population != 0 && peopleVaccinated != 0) {
                Double percentage = (peopleVaccinated / population) * 100.0;
                String vaccinated = String.format("%,.2f", percentage);

                System.out.println("\tVaccinated: ".concat(vaccinated));
                return;
            }
        }

        System.out.println("\tVaccinated: ".concat(NA));
    }

    public void displayHistorical(LiveCasesResponse currentLiveCasesResponseInformation, HistoricalCasesResponse historicalCasesResponse) {
        String newConfirmed = NA;

        if (currentLiveCasesResponseInformation != null && historicalCasesResponse != null) {
            LiveCases liveCases = currentLiveCasesResponseInformation.getAll();
            HistoricalCases historicalCases = historicalCasesResponse.getAll();

            Long newConfirmedCases = liveCases.getConfirmed() - historicalCases.getLatestHistoricalCount();
            newConfirmed = String.format("%,d", newConfirmedCases);
        }

        System.out.println("\tNew confirmed cases: ".concat(newConfirmed));
    }
}
