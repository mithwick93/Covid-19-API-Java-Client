package org.mithwick.covid19.client.models.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InputChoiceTest {

    @Test
    public void getInputChoice_zero_ExitChoice() {
        int input = 0;

        InputChoice actualInputChoice = InputChoice.getInputChoice(input);

        assertEquals(InputChoice.EXIT, actualInputChoice);
    }

    @Test
    public void getInputChoice_one_EnterCountryChoice() {
        int input = 1;

        InputChoice actualInputChoice = InputChoice.getInputChoice(input);

        assertEquals(InputChoice.ENTER_COUNTRY_NAME, actualInputChoice);
    }

    @Test
    public void getInputChoice_invalid_InvalidChoice() {
        int input = 5;

        InputChoice actualInputChoice = InputChoice.getInputChoice(input);

        assertEquals(InputChoice.INVALID, actualInputChoice);
    }

}