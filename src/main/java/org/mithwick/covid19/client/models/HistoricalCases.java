package org.mithwick.covid19.client.models;

import java.util.Map;

public class HistoricalCases {
    private Map<String, Long> dates;

    public Map<String, Long> getDates() {
        return dates;
    }

    public void setDates(Map<String, Long> dates) {
        this.dates = dates;
    }

    @Override
    public String toString() {
        return "HistoricalCases{" +
                "dates=" + dates +
                '}';
    }
}
