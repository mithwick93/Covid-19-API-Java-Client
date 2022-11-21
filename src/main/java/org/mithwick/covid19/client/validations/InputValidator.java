package org.mithwick.covid19.client.validations;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

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

    public static boolean isValidCountryName(String country) {
        return country != null && !"".equals(country.trim()) && countryNames.contains(country);
    }
}
