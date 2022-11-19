package org.mithwick.covid19.client;

import org.mithwick.covid19.client.models.request.InputChoice;
import org.mithwick.covid19.client.services.Covid19APIClientService;
import org.mithwick.covid19.client.validations.InputValidator;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(60))
            .build();

    public static void main(String[] args) {
        System.out.println("Welcome to Covid-19-API Java Client");

        displayMainMenu();
        while (true) {
            InputChoice mainChoice = getMainMenuUserChoice();

            handleExit(mainChoice);

            String country = getCountryName(mainChoice);
            displayCovidInformation(country);
        }
    }

    private static void displayMainMenu() {
        System.out.println("\tPress (1) to enter country name");
        System.out.println("\tPress (0) to exit the program");
    }

    private static InputChoice getMainMenuUserChoice() {
        System.out.print("Please enter your choice: ");

        String input = scanner.nextLine();
        try {
            int mainChoice = Integer.parseInt(input);
            return InputChoice.getInputChoice(mainChoice);
        } catch (NumberFormatException ignored) {
        }

        System.out.println("Invalid input. Please try again");
        return InputChoice.INVALID;
    }

    private static String getCountryName(InputChoice choice) {
        if (!InputChoice.ENTER_COUNTRY_NAME.equals(choice)) {
            return null;
        }

        System.out.print("Please enter ISO 3166-1 alpha-2 compliant country name: ");
        return scanner.nextLine();
    }

    private static void displayCovidInformation(String country) {
        if (country == null) {
            return;
        }

        if (!InputValidator.isValidCountryName(country)) {
            System.out.println("Invalid Country Name. Please try again");
            return;
        }

        Covid19APIClientService covid19APIClientService = new Covid19APIClientService(httpClient, country);
        covid19APIClientService.displayInformation();

    }

    private static void handleExit(InputChoice choice) {
        if (InputChoice.EXIT.equals(choice)) {
            System.out.println("Exiting program.");
            System.exit(0);
        }
    }

}