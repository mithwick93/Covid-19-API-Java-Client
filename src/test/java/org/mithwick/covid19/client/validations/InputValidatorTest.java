package org.mithwick.covid19.client.validations;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InputValidatorTest {

    @Test
    public void isValidCountryName_validCountry_true() {
        String inputCountry = "Sri Lanka";

        boolean actualResult = InputValidator.isValidCountryName(inputCountry);

        assertTrue(actualResult);
    }

    @Test
    public void isValidCountryName_invalidCountry_false() {
        String inputCountry = "sgrg";

        boolean actualResult = InputValidator.isValidCountryName(inputCountry);

        assertFalse(actualResult);
    }

    @Test
    public void isValidCountryName_emptyCountry_false() {
        String inputCountry = "";

        boolean actualResult = InputValidator.isValidCountryName(inputCountry);

        assertFalse(actualResult);
    }

    @Test
    public void isValidCountryName_nullCountry_false() {
        String inputCountry = null;

        boolean actualResult = InputValidator.isValidCountryName(inputCountry);

        assertFalse(actualResult);
    }

}