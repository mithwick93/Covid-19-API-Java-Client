package org.mithwick.covid19.client.utils;

import lombok.AllArgsConstructor;
import org.mithwick.covid19.client.Constants;
import org.mithwick.covid19.client.models.request.InputChoice;

import java.util.Scanner;

@AllArgsConstructor
public class InputProcessor {
    private final Scanner scanner;

    public void displayMainMenu() {
        System.out.println(Constants.MAIN_CHOICE_1_MESSAGE);
        System.out.println(Constants.MAIN_CHOICE_2_MESSAGE);
    }

    public InputChoice getMainMenuUserChoice() {
        System.out.print(Constants.ENTER_MAIN_CHOICE_MESSAGE);

        String input = scanner.nextLine();
        try {
            int mainChoice = Integer.parseInt(input);
            return InputChoice.getInputChoice(mainChoice);
        } catch (NumberFormatException ignored) {
        }
        return InputChoice.INVALID;
    }

    public String getCountryName(InputChoice choice) {
        if (!InputChoice.ENTER_COUNTRY_NAME.equals(choice)) {
            return null;
        }

        System.out.print(Constants.ENTER_COUNTRY_MESSAGE);
        return scanner.nextLine().trim();
    }

    public void handleInvalidInput(InputChoice choice) {
        if (InputChoice.INVALID.equals(choice)) {
            System.out.println(Constants.INVALID_MAIN_CHOICE_MESSAGE);
        }
    }

    public void handleExit(InputChoice choice) {
        if (InputChoice.EXIT.equals(choice)) {
            System.out.println(Constants.EXIT_PROGRAM_MESSAGE);
            System.exit(0);
        }
    }
}
