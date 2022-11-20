package org.mithwick.covid19.client.services;

import org.mithwick.covid19.client.models.HistoricalCases;
import org.mithwick.covid19.client.models.LiveCases;
import org.mithwick.covid19.client.models.Vaccines;
import org.mithwick.covid19.client.models.response.Covid19APIResponse;
import org.mithwick.covid19.client.services.utils.Covid19APIUtil;

public class Covid19APIClientService {

    private static final String NA = "N/A";
    private final Covid19APIUtil covid19APIUtil;
    private String country;

    public Covid19APIClientService(Covid19APIUtil covid19APIUtil) {
        this.covid19APIUtil = covid19APIUtil;
    }

    public void setCountry(String country) {
        this.country = country;
        covid19APIUtil.setCountry(country);
    }

    public void displayInformation() {
        Covid19APIResponse<LiveCases> liveCasesResponse = covid19APIUtil.doGetRequest(covid19APIUtil.getCurrentInformationURI(), LiveCases.class);
        Covid19APIResponse<Vaccines> vaccinesResponse = covid19APIUtil.doGetRequest(covid19APIUtil.getVaccineInformationURI(), Vaccines.class);
        Covid19APIResponse<HistoricalCases> historicalCasesResponse = covid19APIUtil.doGetRequest(covid19APIUtil.getHistoricalInformationURI(), HistoricalCases.class);

        System.out.println("Displaying Covid-19 Information of ".concat(country));

        displayLiveCases(liveCasesResponse);
        displayVaccines(vaccinesResponse);
        displayHistorical(liveCasesResponse, historicalCasesResponse);
    }

    private void displayLiveCases(Covid19APIResponse<LiveCases> liveCasesResponse) {
        String confirmed = NA;
        String recovered = NA;
        String deaths = NA;

        if (liveCasesResponse != null && liveCasesResponse.getData() != null) {
            LiveCases liveCases = liveCasesResponse.getData();

            confirmed = String.format("%,d", liveCases.getConfirmed());
            recovered = String.format("%,d", liveCases.getRecovered());
            deaths = String.format("%,d", liveCases.getDeaths());
        }

        System.out.println("\tConfirmed: ".concat(confirmed));
        System.out.println("\tRecovered: ".concat(recovered));
        System.out.println("\tDeaths: ".concat(deaths));
    }

    private void displayVaccines(Covid19APIResponse<Vaccines> vaccinesResponse) {
        if (vaccinesResponse != null && vaccinesResponse.getData() != null) {
            Vaccines vaccines = vaccinesResponse.getData();
            long population = vaccines.getPopulation();
            long peopleVaccinated = vaccines.getPeopleVaccinated();

            if (population != 0) {
                Double percentage = (peopleVaccinated / population) * 100.0;
                String vaccinated = String.format("%,.2f", percentage);

                System.out.println("\tVaccinated: ".concat(vaccinated));
                return;
            }
        }

        System.out.println("\tVaccinated: ".concat(NA));
    }

    private void displayHistorical(Covid19APIResponse<LiveCases> liveCasesResponse, Covid19APIResponse<HistoricalCases> historicalCasesResponse) {
        String newConfirmed = NA;

        if (liveCasesResponse != null && liveCasesResponse.getData() != null && historicalCasesResponse != null && historicalCasesResponse.getData() != null) {
            LiveCases liveCases = liveCasesResponse.getData();
            HistoricalCases historicalCases = historicalCasesResponse.getData();

            Long newConfirmedCases = liveCases.getConfirmed() - historicalCases.getLatestHistoricalCount();
            newConfirmed = String.format("%,d", newConfirmedCases);
        }

        System.out.println("\tNew confirmed cases: ".concat(newConfirmed));
    }
}
