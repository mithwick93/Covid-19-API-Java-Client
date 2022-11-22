package org.mithwick.covid19.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mithwick.covid19.client.models.Covid19Information;
import org.mithwick.covid19.client.models.request.InputChoice;
import org.mithwick.covid19.client.services.Covid19APIClientService;
import org.mithwick.covid19.client.utils.InputProcessor;
import org.mithwick.covid19.client.validations.InputValidator;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.Scanner;

public class Main {
    /**
     * main method would act as the bootstrap. It will create required objects and handover the execution to run method
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(60))
                .build();

        InputProcessor inputProcessor = new InputProcessor(scanner);
        Covid19APIClientService covid19APIClientService = new Covid19APIClientService(objectMapper, httpClient);

        Main main = new Main();
        main.run(inputProcessor, covid19APIClientService);
    }

    /**
     * Main entry point to the application
     */
    public void run(InputProcessor inputProcessor, Covid19APIClientService covid19APIClientService) {
        inputProcessor.displayMainMenu();
        while (true) {
            InputChoice mainChoice = inputProcessor.getMainMenuUserChoice();

            switch (mainChoice) {
                case ENTER_COUNTRY_NAME -> {
                    String country = inputProcessor.getCountryName();
                    displayCovidInformation(country, covid19APIClientService);
                }
                case INVALID -> System.out.println(Constants.INVALID_MAIN_CHOICE_MESSAGE);
                case EXIT -> {
                    System.out.println(Constants.EXIT_PROGRAM_MESSAGE);
                    return;
                }

            }
        }
    }

    /**
     * Method to execute display Covid information
     */
    public void displayCovidInformation(String country, Covid19APIClientService covid19APIClientService) {
        if (country == null) {
            return;
        }

        if (!InputValidator.isValidCountryName(country)) {
            System.out.println(Constants.INVALID_COUNTRY_MESSAGE);
            return;
        }

        Covid19Information covid19Information = covid19APIClientService.getCovid19Information(country);
        covid19Information.prettyPrint();
    }
}