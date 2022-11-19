package org.mithwick.covid19.client.validations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class InputValidator {
    private static final Set<String> countryNames;

    static {
        countryNames = new HashSet<>();
        Arrays.stream(Locale.getAvailableLocales()).forEach(locale -> countryNames.add(locale.getDisplayCountry()));
    }

    public static Set<String> getValidCountryNames() {
        return countryNames;
    }

    public static boolean isValidCountryName(String country) {
        return country != null && !"".equals(country.trim()) && countryNames.contains(country);
    }
}
