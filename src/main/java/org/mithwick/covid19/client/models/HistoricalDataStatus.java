package org.mithwick.covid19.client.models;

import lombok.Getter;

/**
 * Available statuses for historical data
 */
@Getter
public enum HistoricalDataStatus {
    CONFIRMED("confirmed"), DEATHS("deaths"), RECOVERED("recovered");

    private final String value;

    HistoricalDataStatus(String value) {
        this.value = value;
    }
}
