package org.mithwick.covid19.client.validations;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.mithwick.covid19.client.Constants;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InputValidator {
    private static final Set<String> countryNames;

    static {
        countryNames = new HashSet<>();
        Arrays.stream(Locale.getAvailableLocales()).forEach(locale -> countryNames.add(locale.getDisplayCountry()));
    }

    /**
     * Check if the country is ISO 3166-1 alpha-2 compliant. Does not allow  null or empty strings
     *
     * @param country the country to be checked
     * @return if the given country name is valid or not
     */
    public static boolean isValidCountryName(String country) {
        return country != null && !Constants.EMPTY_STRING.equals(country.trim()) && countryNames.contains(country);
    }
}
