package org.mithwick.covid19.client.utils;

import org.mithwick.covid19.client.models.request.InputChoice;

import java.util.Scanner;

public class InputProcessor {
    private static final Scanner scanner = new Scanner(System.in);

    public void displayMainMenu() {
        System.out.println("\tPress 1 to enter country name");
        System.out.println("\tPress 0 to exit the program");
    }

    public  InputChoice getMainMenuUserChoice() {
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

    public String getCountryName(InputChoice choice) {
        if (!InputChoice.ENTER_COUNTRY_NAME.equals(choice)) {
            return null;
        }

        System.out.print("Please enter ISO 3166-1 alpha-2 compliant country name: ");
        return scanner.nextLine();
    }

    public void handleExit(InputChoice choice) {
        if (InputChoice.EXIT.equals(choice)) {
            System.out.println("Exiting program.");
            System.exit(0);
        }
    }
}
