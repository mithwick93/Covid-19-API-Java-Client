package org.mithwick.covid19.client.models;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public class Covid19Information {
    private static final String NA = "N/A";

    private final String country;

    private LiveCases liveCases;
    private HistoricalCases historicalCases;
    private Vaccines vaccines;

    public Covid19Information(String country) {
        this.country = country;
    }

    public LiveCases getLiveCases() {
        return liveCases;
    }

    public void setLiveCases(LiveCases liveCases) {
        this.liveCases = liveCases;
    }

    public HistoricalCases getHistoricalCases() {
        return historicalCases;
    }

    public void setHistoricalCases(HistoricalCases historicalCases) {
        this.historicalCases = historicalCases;
    }

    public Vaccines getVaccines() {
        return vaccines;
    }

    public void setVaccines(Vaccines vaccines) {
        this.vaccines = vaccines;
    }

    public String getConfirmedCases() {
        String confirmed = NA;

        if (getLiveCases() != null) {
            confirmed = String.format("%,d", getLiveCases().getConfirmed());
        }

        return "Confirmed: ".concat(confirmed);
    }

    public String getRecoveredCases() {
        String recovered = NA;

        if (getLiveCases() != null) {
            recovered = String.format("%,d", getLiveCases().getRecovered());
        }

        return "Recovered: ".concat(recovered);
    }

    public String getDeathCases() {
        String deaths = NA;

        if (getLiveCases() != null) {
            deaths = String.format("%,d", getLiveCases().getDeaths());
        }

        return "Deaths: ".concat(deaths);
    }

    public String getVaccinatedPercentage() {
        String vaccinatedPercentage = NA;

        if (getVaccines() != null) {
            long population = getVaccines().getPopulation();
            long peopleVaccinated = getVaccines().getPeopleVaccinated();

            if (population != 0) {
                double percentage = ((double) peopleVaccinated / population) * 100.0;
                vaccinatedPercentage = String.format("%,.2f", percentage);
            }
        }

        return "Vaccinated: ".concat(vaccinatedPercentage);
    }

    public String getNewConfirmedCases() {
        String newConfirmed = NA;

        if (getLiveCases() != null && getHistoricalCases() != null) {
            Long newConfirmedCases = getLiveCases().getConfirmed() - getLatestHistoricalCount();
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
        Map<String, Long> dates = getHistoricalCases().getDates();
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
