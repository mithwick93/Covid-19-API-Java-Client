package org.mithwick.covid19.client.models;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Covid19InformationTest {
    private static final String country = "Sri Lanka";

    @Test
    public void getConfirmedCases_liveDataNull_NA() {
        String expectedConfirmedCases = "Confirmed: N/A";

        Covid19Information covid19Information = new Covid19Information(country);

        String confirmedCases = covid19Information.getConfirmedCases();

        assertEquals(expectedConfirmedCases, confirmedCases);
    }

    @Test
    public void getConfirmedCases_liveDataSet_correctResult() {
        long confirmedCasesNumber = 105;
        LiveData liveData = new LiveData();
        liveData.setConfirmed(confirmedCasesNumber);

        String expectedConfirmedCases = "Confirmed: 105";

        Covid19Information covid19Information = new Covid19Information(country);
        covid19Information.setLiveData(liveData);

        String confirmedCases = covid19Information.getConfirmedCases();

        assertEquals(expectedConfirmedCases, confirmedCases);
    }

    @Test
    public void getConfirmedCases_liveDataSet_correctResultThousandSeperated() {
        long confirmedCasesNumber = 1243526;
        LiveData liveData = new LiveData();
        liveData.setConfirmed(confirmedCasesNumber);

        String expectedConfirmedCases = "Confirmed: 1,243,526";

        Covid19Information covid19Information = new Covid19Information(country);
        covid19Information.setLiveData(liveData);

        String confirmedCases = covid19Information.getConfirmedCases();

        assertEquals(expectedConfirmedCases, confirmedCases);
    }

    @Test
    public void getRecoveredCases_liveDataNull_NA() {
        String expectedRecoveredCases = "Recovered: N/A";

        Covid19Information covid19Information = new Covid19Information(country);

        String recoveredCases = covid19Information.getRecoveredCases();

        assertEquals(expectedRecoveredCases, recoveredCases);
    }

    @Test
    public void getRecoveredCases_liveDataSet_correctResult() {
        long recoveredCasesNumber = 451;
        LiveData liveData = new LiveData();
        liveData.setRecovered(recoveredCasesNumber);

        String expectedRecoveredCases = "Recovered: 451";

        Covid19Information covid19Information = new Covid19Information(country);
        covid19Information.setLiveData(liveData);

        String recoveredCases = covid19Information.getRecoveredCases();

        assertEquals(expectedRecoveredCases, recoveredCases);
    }

    @Test
    public void getRecoveredCases_liveDataSet_correctResultThousandSeperated() {
        long recoveredCasesNumber = 36574767;
        LiveData liveData = new LiveData();
        liveData.setRecovered(recoveredCasesNumber);

        String expectedRecoveredCases = "Recovered: 36,574,767";

        Covid19Information covid19Information = new Covid19Information(country);
        covid19Information.setLiveData(liveData);

        String recoveredCases = covid19Information.getRecoveredCases();

        assertEquals(expectedRecoveredCases, recoveredCases);
    }

    @Test
    public void getDeathCases_liveDataNull_NA() {
        String expectedDeathCases = "Deaths: N/A";

        Covid19Information covid19Information = new Covid19Information(country);

        String deathCases = covid19Information.getDeathCases();

        assertEquals(expectedDeathCases, deathCases);
    }

    @Test
    public void getDeathCases_liveDataSet_correctResult() {
        long deathCasesNumber = 475;
        LiveData liveData = new LiveData();
        liveData.setDeaths(deathCasesNumber);

        String expectedDeathCases = "Deaths: 475";

        Covid19Information covid19Information = new Covid19Information(country);
        covid19Information.setLiveData(liveData);

        String deathCases = covid19Information.getDeathCases();

        assertEquals(expectedDeathCases, deathCases);
    }

    @Test
    public void getDeathCases_liveDataSet_correctResultThousandSeperated() {
        long deathCasesNumber = 89768743;
        LiveData liveData = new LiveData();
        liveData.setDeaths(deathCasesNumber);

        String expectedDeathCases = "Deaths: 89,768,743";

        Covid19Information covid19Information = new Covid19Information(country);
        covid19Information.setLiveData(liveData);

        String deathCases = covid19Information.getDeathCases();

        assertEquals(expectedDeathCases, deathCases);
    }

    @Test
    public void getVaccinatedPercentage_vaccineDataNull_NA() {
        String expectedVaccinatedPercentage = "Vaccinated: N/A";

        Covid19Information covid19Information = new Covid19Information(country);

        String vaccinatedPercentage = covid19Information.getVaccinatedPercentage();

        assertEquals(expectedVaccinatedPercentage, vaccinatedPercentage);
    }

    @Test
    public void getVaccinatedPercentage_vaccineDataSetWithZeroTotalPopulation_NA() {
        long totalPopulation = 0;
        long vaccinatedPopulation = 132;
        VaccineData vaccineData = new VaccineData();
        vaccineData.setPopulation(totalPopulation);
        vaccineData.setPeopleVaccinated(vaccinatedPopulation);

        String expectedVaccinatedPercentage = "Vaccinated: N/A";

        Covid19Information covid19Information = new Covid19Information(country);
        covid19Information.setVaccineData(vaccineData);

        String vaccinatedPercentage = covid19Information.getVaccinatedPercentage();

        assertEquals(expectedVaccinatedPercentage, vaccinatedPercentage);
    }

    @Test
    public void getVaccinatedPercentage_vaccineDataSet_correctResult() {
        long totalPopulation = 475;
        long vaccinatedPopulation = 132;
        VaccineData vaccineData = new VaccineData();
        vaccineData.setPopulation(totalPopulation);
        vaccineData.setPeopleVaccinated(vaccinatedPopulation);

        String expectedVaccinatedPercentage = "Vaccinated: 27.79%";

        Covid19Information covid19Information = new Covid19Information(country);
        covid19Information.setVaccineData(vaccineData);

        String vaccinatedPercentage = covid19Information.getVaccinatedPercentage();

        assertEquals(expectedVaccinatedPercentage, vaccinatedPercentage);
    }

    @Test
    public void getNewConfirmedCases_liveDataAndHistoryDataNull_NA() {
        String expectedNewConfirmedCases = "New confirmed cases: N/A";

        Covid19Information covid19Information = new Covid19Information(country);

        String newConfirmedCases = covid19Information.getNewConfirmedCases();

        assertEquals(expectedNewConfirmedCases, newConfirmedCases);
    }

    @Test
    public void getNewConfirmedCases_historyDataNull_NA() {
        String expectedNewConfirmedCases = "New confirmed cases: N/A";

        Covid19Information covid19Information = new Covid19Information(country);
        covid19Information.setLiveData(new LiveData());

        String newConfirmedCases = covid19Information.getNewConfirmedCases();

        assertEquals(expectedNewConfirmedCases, newConfirmedCases);
    }

    @Test
    public void getNewConfirmedCases_liveDataNull_NA() {
        String expectedNewConfirmedCases = "New confirmed cases: N/A";

        Covid19Information covid19Information = new Covid19Information(country);
        covid19Information.setHistoricalData(new HistoricalData());

        String newConfirmedCases = covid19Information.getNewConfirmedCases();

        assertEquals(expectedNewConfirmedCases, newConfirmedCases);
    }

    @Test
    public void getNewConfirmedCases_allDataSet_correctResult() {
        long confirmedCasesNumber = 30500;
        LiveData liveData = new LiveData();
        liveData.setConfirmed(confirmedCasesNumber);

        HistoricalData historicalData = new HistoricalData();
        Map<String, Long> dates = new HashMap<>();
        dates.put("2020-12-25", 29580L);
        dates.put("2020-12-24", 29330L);
        dates.put("2020-12-23", 28909L);
        historicalData.setDates(dates);

        String expectedNewConfirmedCases = "New confirmed cases: 920";

        Covid19Information covid19Information = new Covid19Information(country);
        covid19Information.setLiveData(liveData);
        covid19Information.setHistoricalData(historicalData);

        String newConfirmedCases = covid19Information.getNewConfirmedCases();

        assertEquals(expectedNewConfirmedCases, newConfirmedCases);
    }

    @Test
    public void getNewConfirmedCases_HistoricalDataEmpty_correctResult() {
        long confirmedCasesNumber = 30500;
        LiveData liveData = new LiveData();
        liveData.setConfirmed(confirmedCasesNumber);

        HistoricalData historicalData = new HistoricalData();
        Map<String, Long> dates = new HashMap<>();
        historicalData.setDates(dates);

        String expectedNewConfirmedCases = "New confirmed cases: 30,500";

        Covid19Information covid19Information = new Covid19Information(country);
        covid19Information.setLiveData(liveData);
        covid19Information.setHistoricalData(historicalData);

        String newConfirmedCases = covid19Information.getNewConfirmedCases();

        assertEquals(expectedNewConfirmedCases, newConfirmedCases);
    }
}