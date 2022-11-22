package org.mithwick.covid19.client.utils;

import lombok.AllArgsConstructor;
import org.mithwick.covid19.client.Constants;
import org.mithwick.covid19.client.models.request.InputChoice;

import java.util.Scanner;

@AllArgsConstructor
public class InputProcessor {
    private final Scanner scanner;

    public void displayMainMenu() {
        System.out.println(Constants.WELCOME_MESSAGE);
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
            // we return invalid input choice if the user input cannot be converted to an int
        }
        return InputChoice.INVALID;
    }

    public String getCountryName() {
        System.out.print(Constants.ENTER_COUNTRY_MESSAGE);
        return scanner.nextLine().trim();
    }
}
