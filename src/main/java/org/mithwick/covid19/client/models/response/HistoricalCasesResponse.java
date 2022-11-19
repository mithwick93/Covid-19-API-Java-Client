package org.mithwick.covid19.client.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mithwick.covid19.client.models.HistoricalCases;

public class HistoricalCasesResponse {
    @JsonProperty("All")
    private HistoricalCases all;

    public HistoricalCases getAll() {
        return all;
    }

    public void setAll(HistoricalCases all) {
        this.all = all;
    }

    @Override
    public String toString() {
        return "HistoricalCasesResponse{" +
                "all=" + all +
                '}';
    }
}
