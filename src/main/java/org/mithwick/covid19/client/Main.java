package org.mithwick.covid19.client;

import org.mithwick.covid19.client.models.request.InputChoice;
import org.mithwick.covid19.client.services.Covid19APIClientService;
import org.mithwick.covid19.client.services.utils.Covid19APIUtil;
import org.mithwick.covid19.client.utils.InputProcessor;
import org.mithwick.covid19.client.validations.InputValidator;

import java.net.http.HttpClient;
import java.time.Duration;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Covid-19-API Java Client");

        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(60))
                .build();
        Covid19APIUtil covid19APIUtil = new Covid19APIUtil(httpClient);
        Covid19APIClientService covid19APIClientService = new Covid19APIClientService(covid19APIUtil);

        InputProcessor inputProcessor = new InputProcessor();

        inputProcessor.displayMainMenu();
        while (true) {
            InputChoice mainChoice = inputProcessor.getMainMenuUserChoice();

            inputProcessor.handleExit(mainChoice);

            String country = inputProcessor.getCountryName(mainChoice);
            displayCovidInformation(country, covid19APIClientService);
        }
    }

    public static void displayCovidInformation(String country, Covid19APIClientService covid19APIClientService) {
        if (country == null) {
            return;
        }

        if (!InputValidator.isValidCountryName(country)) {
            System.out.println("Invalid Country Name. Please try again");
            return;
        }

        covid19APIClientService.setCountry(country);
        covid19APIClientService.displayInformation();
    }
}