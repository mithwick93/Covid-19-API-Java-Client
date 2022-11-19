package org.mithwick.covid19.client.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Vaccines {
    private Long population;

    @JsonProperty("people_vaccinated")
    private Long peopleVaccinated;

    public Long getPopulation() {
        return population;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    public Long getPeopleVaccinated() {
        return peopleVaccinated;
    }

    public void setPeopleVaccinated(Long peopleVaccinated) {
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
