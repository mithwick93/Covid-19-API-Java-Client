package org.mithwick.covid19.client.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mithwick.covid19.client.models.LiveCases;

public class LiveCasesResponse {
    @JsonProperty("All")
    private LiveCases all;

    public LiveCases getAll() {
        return all;
    }

    public void setAll(LiveCases all) {
        this.all = all;
    }

    @Override
    public String toString() {
        return "LiveCasesResponse{" +
                "all=" + all +
                '}';
    }
}
