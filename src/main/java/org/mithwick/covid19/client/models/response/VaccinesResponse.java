package org.mithwick.covid19.client.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mithwick.covid19.client.models.Vaccines;

public class VaccinesResponse {
    @JsonProperty("All")
    private Vaccines all;

    public Vaccines getAll() {
        return all;
    }

    public void setAll(Vaccines all) {
        this.all = all;
    }

    @Override
    public String toString() {
        return "VaccinesResponse{" +
                "all=" + all +
                '}';
    }
}
