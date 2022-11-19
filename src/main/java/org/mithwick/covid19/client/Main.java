package org.mithwick.covid19.client;

import org.mithwick.covid19.client.models.InputChoice;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to Covid-19-API Java Client");

        displayMainMenu();
        while (true) {
            InputChoice mainChoice = getMainMenuUserChoice();

            handleExit(mainChoice);
            String country = getCountryName(mainChoice);
        }
    }

    private static void displayMainMenu() {
        System.out.println("\tPress (1) to enter country name");
        System.out.println("\tPress (0) to exit the program");
    }

    private static InputChoice getMainMenuUserChoice() {
        System.out.print("Please enter your choice:");

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
        String country = null;

        if (InputChoice.ENTER_COUNTRY_NAME.equals(choice)) {
            return country;
        }

        System.out.print("Please enter ISO 3166-1 alpha-2 compliant country name:");
        country = scanner.nextLine();

        // todo - validation
        return country;
    }

    private static void handleExit(InputChoice choice) {
        if (InputChoice.EXIT.equals(choice)) {
            System.out.println("Exiting program.");
            System.exit(0);
        }
    }

}