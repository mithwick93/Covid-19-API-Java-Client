package org.mithwick.covid19.client.models;

import lombok.Data;

import java.util.Map;

@Data
public class HistoricalData {
    private Map<String, Long> dates;
}
