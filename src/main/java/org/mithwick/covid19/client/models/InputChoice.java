package org.mithwick.covid19.client.models;

public enum InputChoice {
    EXIT, ENTER_COUNTRY_NAME, INVALID;

    public static InputChoice getInputChoice(int choice) {
        return switch (choice) {
            case 0 -> InputChoice.EXIT;
            case 1 -> InputChoice.ENTER_COUNTRY_NAME;
            default -> InputChoice.INVALID;
        };
    }
}
