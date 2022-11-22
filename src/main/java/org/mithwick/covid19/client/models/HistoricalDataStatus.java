package org.mithwick.covid19.client.models;

import lombok.Getter;

@Getter
public enum HistoricalDataStatus {
    CONFIRMED("confirmed"), DEATHS("deaths"), RECOVERED("recovered");

    private final String value;

    HistoricalDataStatus(String value) {
        this.value = value;
    }
}
