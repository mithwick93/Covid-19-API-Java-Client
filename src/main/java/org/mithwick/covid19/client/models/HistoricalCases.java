package org.mithwick.covid19.client.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Map;

public class HistoricalCases {

    private Map<String, Long> dates;

    @JsonIgnore
    private Long latestHistoricalCount;

    public Map<String, Long> getDates() {
        return dates;
    }

    public void setDates(Map<String, Long> dates) {
        this.dates = dates;
        this.setLatestHistoricalCount(dates);
    }

    @JsonIgnore
    public void setLatestHistoricalCount(Map<String, Long> dates) {
        ArrayList<String> datesList = new ArrayList<>(dates.keySet());
        datesList
                .stream()
                .max(String::compareTo)
                .ifPresent(
                        maxDate -> this.latestHistoricalCount = dates.get(maxDate)
                );
    }

    public Long getLatestHistoricalCount() {
        return latestHistoricalCount;
    }

    @Override
    public String toString() {
        return "HistoricalCases{\n" +
                "dates=" + dates +
                ", \n" +
                "latestHistoricalCount=" + latestHistoricalCount +
                "\n}";
    }
}
