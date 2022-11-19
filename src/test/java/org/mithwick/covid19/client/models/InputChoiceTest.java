package org.mithwick.covid19.client.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InputChoiceTest {

    @Test
    public void testReturnExitChoice() {
        int input = 0;

        InputChoice actualInputChoice = InputChoice.getInputChoice(input);

        assertEquals(InputChoice.EXIT, actualInputChoice);
    }

    @Test
    public void testReturnEnterCountryChoice() {
        int input = 1;

        InputChoice actualInputChoice = InputChoice.getInputChoice(input);

        assertEquals(InputChoice.ENTER_COUNTRY_NAME, actualInputChoice);
    }

    @Test
    public void testInvalidChoice() {
        int input = 5;

        InputChoice actualInputChoice = InputChoice.getInputChoice(input);

        assertEquals(InputChoice.INVALID, actualInputChoice);
    }

}