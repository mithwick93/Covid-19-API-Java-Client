package org.mithwick.covid19.client;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class Constants {
    public static final String BASE_URL = "https://covid-api.mmediagroup.fr/v1";
    public static final String CURRENT_DATA_PATH = "/cases?country=";
    public static final String HISTORY_DATA_PATH = "/history?status=confirmed&country=";
    public static final String VACCINE_DATA_PATH = "/vaccines?country=";
    public static final String CONTENT_TYPE_KEY = "Content-Type";
    public static final String CONTENT_TYPE_JSON = "application/json";

    public static final String WELCOME_MESSAGE = "Welcome to Covid-19-API Java Client";
    public static final String MAIN_CHOICE_1_MESSAGE = "\tPress 1 to enter country name";
    public static final String MAIN_CHOICE_2_MESSAGE = "\tPress 0 to exit the program";
    public static final String ENTER_MAIN_CHOICE_MESSAGE = "Please enter your choice: ";
    public static final String ENTER_COUNTRY_MESSAGE = "Please enter ISO 3166-1 alpha-2 compliant country name: ";
    public static final String INVALID_MAIN_CHOICE_MESSAGE = "Invalid input. Please try again";
    public static final String INVALID_COUNTRY_MESSAGE = "Invalid Country Name. Please try again";
    public static final String DISPLAY_COUNTRY_INFORMATION = "Displaying Covid-19 Information of ";
    public static final String EXIT_PROGRAM_MESSAGE = "Exiting program.";


    public static final String CONFIRMED_PREFIX = "Confirmed: ";
    public static final String RECOVERED_PREFIX = "Recovered: ";
    public static final String DEATHS_PREFIX = "Deaths: ";
    public static final String VACCINATED_PREFIX = "Vaccinated: ";
    public static final String NEW_CONFIRMED_PREFIX = "New confirmed cases: ";

    public static final String EMPTY_STRING = "";
    public static final String NA = "N/A";
    public static final String PERCENTAGE_SYMBOL = "%";
    public static final String THOUSAND_SEPARATOR_FORMAT = "%,d";
    public static final String DOUBLE_ROUND_OFF_FORMAT = "%,.2f";
    public static final String TAB_CHARACTER = "\t";

    public static final String FETCHING_MESSAGE_PREFIX = "\tFetching ";
    public static final String FETCHING_MESSAGE_PREFIX_SUFFIX = " information";
    public static final String FETCHING_MESSAGE_END = " - done";
    public static final String FETCHING_MESSAGE_ERROR = "\n\tAn error occurred: ";
}
