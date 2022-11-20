package org.mithwick.covid19.client.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VaccineData {
    private long population;

    @JsonProperty("people_vaccinated")
    private long peopleVaccinated;
}
