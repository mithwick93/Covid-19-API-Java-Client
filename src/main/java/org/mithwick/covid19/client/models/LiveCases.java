package org.mithwick.covid19.client.models;

public class LiveCases {
    private Long confirmed;
    private Long recovered;
    private Long deaths;

    private String updated;

    public Long getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Long confirmed) {
        this.confirmed = confirmed;
    }

    public Long getRecovered() {
        return recovered;
    }

    public void setRecovered(Long recovered) {
        this.recovered = recovered;
    }

    public Long getDeaths() {
        return deaths;
    }

    public void setDeaths(Long deaths) {
        this.deaths = deaths;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "LiveCases{" +
                "confirmed=" + confirmed +
                ", recovered=" + recovered +
                ", deaths=" + deaths +
                ", updated='" + updated + '\'' +
                '}';
    }
}
