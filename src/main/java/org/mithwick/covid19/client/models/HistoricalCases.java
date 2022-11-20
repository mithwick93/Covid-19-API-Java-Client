package org.mithwick.covid19.client.models;

import java.util.ArrayList;
import java.util.Map;

public class HistoricalCases {

    private Map<String, Long> dates;
    private long latestHistoricalCount;

    public Map<String, Long> getDates() {
        return dates;
    }

    public long getLatestHistoricalCount() {
        return latestHistoricalCount;
    }

    public void setDates(Map<String, Long> dates) {
        this.dates = dates;
        this.setLatestHistoricalCount(dates);
    }

    private void setLatestHistoricalCount(Map<String, Long> dates) {
        ArrayList<String> datesList = new ArrayList<>(dates.keySet());
        datesList
                .stream()
                .max(String::compareTo) // we can use string compare to get the latest date because strings data is formatted
                .ifPresent(
                        maxDate -> this.latestHistoricalCount = dates.get(maxDate)
                );
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
