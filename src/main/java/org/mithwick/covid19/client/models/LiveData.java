package org.mithwick.covid19.client.models;

import lombok.Data;

@Data
public class LiveData {
    private long confirmed;
    private long recovered;
    private long deaths;
}
