package org.mithwick.covid19.client.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Vaccines {
    private long population;

    @JsonProperty("people_vaccinated")
    private long peopleVaccinated;

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public long getPeopleVaccinated() {
        return peopleVaccinated;
    }

    public void setPeopleVaccinated(long peopleVaccinated) {
        this.peopleVaccinated = peopleVaccinated;
    }

    @Override
    public String toString() {
        return "Vaccines{" +
                "population=" + population +
                ", peopleVaccinated=" + peopleVaccinated +
                '}';
    }
}
