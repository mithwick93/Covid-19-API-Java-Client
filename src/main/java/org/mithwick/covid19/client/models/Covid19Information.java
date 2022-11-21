package org.mithwick.covid19.client.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.mithwick.covid19.client.Constants;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
public class Covid19Information {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final String country;

    private LiveData liveData;
    private HistoricalData historicalData;
    private VaccineData vaccineData;

    public Covid19Information(String country) {
        this.country = country;
    }

    public String getConfirmedCases() {
        String confirmed = Constants.NA;

        if (getLiveData() != null) {
            confirmed = String.format(Constants.THOUSAND_SEPARATOR_FORMAT, getLiveData().getConfirmed());
        }

        return Constants.CONFIRMED_PREFIX.concat(confirmed);
    }

    public String getRecoveredCases() {
        String recovered = Constants.NA;

        if (getLiveData() != null) {
            recovered = String.format(Constants.THOUSAND_SEPARATOR_FORMAT, getLiveData().getRecovered());
        }

        return Constants.RECOVERED_PREFIX.concat(recovered);
    }

    public String getDeathCases() {
        String deaths = Constants.NA;

        if (getLiveData() != null) {
            deaths = String.format(Constants.THOUSAND_SEPARATOR_FORMAT, getLiveData().getDeaths());
        }

        return Constants.DEATHS_PREFIX.concat(deaths);
    }

    public String getVaccinatedPercentage() {
        if (getVaccineData() != null) {
            long population = getVaccineData().getPopulation();
            long peopleVaccinated = getVaccineData().getPeopleVaccinated();

            if (population != 0) {
                double percentage = ((double) peopleVaccinated / population) * 100.0;
                String vaccinatedPercentage = String.format(Constants.DOUBLE_ROUND_OFF_FORMAT, percentage);

                return Constants.VACCINATED_PREFIX.concat(vaccinatedPercentage).concat(Constants.PERCENTAGE_SYMBOL);
            }
        }

        return Constants.VACCINATED_PREFIX.concat(Constants.NA);
    }

    public String getNewConfirmedCases() {
        String newConfirmed = Constants.NA;

        if (getLiveData() != null && getHistoricalData() != null) {
            Long newConfirmedCases = getLiveData().getConfirmed() - getLatestHistoricalCount();
            newConfirmed = String.format(Constants.THOUSAND_SEPARATOR_FORMAT, newConfirmedCases);
        }

        return Constants.NEW_CONFIRMED_PREFIX.concat(newConfirmed);
    }

    public void prettyPrint() {
        System.out.println(Constants.DISPLAY_COUNTRY_INFORMATION.concat(country));

        System.out.println(Constants.TAB_CHARACTER.concat(getConfirmedCases()));
        System.out.println(Constants.TAB_CHARACTER.concat(getRecoveredCases()));
        System.out.println(Constants.TAB_CHARACTER.concat(getDeathCases()));

        System.out.println(Constants.TAB_CHARACTER.concat(getVaccinatedPercentage()));

        System.out.println(Constants.TAB_CHARACTER.concat(getNewConfirmedCases()));
    }

    private long getLatestHistoricalCount() {
        Map<String, Long> dates = getHistoricalData().getDates();
        ArrayList<String> datesList = new ArrayList<>(dates.keySet());
        Optional<String> maxDateOptional = datesList
                .stream()
                .max(String::compareTo);// we can use string compare to get the latest date because strings data is formatted;

        if (maxDateOptional.isPresent()) {
            return dates.get(maxDateOptional.get());
        }

        return 0;
    }

}
