package org.mithwick.covid19.client.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
public class Covid19Information {
    private static final String NA = "N/A";

    private final String country;

    private LiveData liveData;
    private HistoricalData historicalData;
    private VaccineData vaccineData;

    public Covid19Information(String country) {
        this.country = country;
    }

    public String getConfirmedCases() {
        String confirmed = NA;

        if (getLiveData() != null) {
            confirmed = String.format("%,d", getLiveData().getConfirmed());
        }

        return "Confirmed: ".concat(confirmed);
    }

    public String getRecoveredCases() {
        String recovered = NA;

        if (getLiveData() != null) {
            recovered = String.format("%,d", getLiveData().getRecovered());
        }

        return "Recovered: ".concat(recovered);
    }

    public String getDeathCases() {
        String deaths = NA;

        if (getLiveData() != null) {
            deaths = String.format("%,d", getLiveData().getDeaths());
        }

        return "Deaths: ".concat(deaths);
    }

    public String getVaccinatedPercentage() {
        if (getVaccineData() != null) {
            long population = getVaccineData().getPopulation();
            long peopleVaccinated = getVaccineData().getPeopleVaccinated();

            if (population != 0) {
                double percentage = ((double) peopleVaccinated / population) * 100.0;
                String vaccinatedPercentage = String.format("%,.2f", percentage);

                return "Vaccinated: ".concat(vaccinatedPercentage).concat("%");
            }
        }

        return "Vaccinated: ".concat(NA);
    }

    public String getNewConfirmedCases() {
        String newConfirmed = NA;

        if (getLiveData() != null && getHistoricalData() != null) {
            Long newConfirmedCases = getLiveData().getConfirmed() - getLatestHistoricalCount();
            newConfirmed = String.format("%,d", newConfirmedCases);
        }

        return "New confirmed cases: ".concat(newConfirmed);
    }

    public void prettyPrint() {
        System.out.println("Displaying Covid-19 Information of ".concat(country));
        System.out.println("\t".concat(getConfirmedCases()));
        System.out.println("\t".concat(getRecoveredCases()));
        System.out.println("\t".concat(getDeathCases()));
        System.out.println("\t".concat(getVaccinatedPercentage()));
        System.out.println("\t".concat(getNewConfirmedCases()));
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
