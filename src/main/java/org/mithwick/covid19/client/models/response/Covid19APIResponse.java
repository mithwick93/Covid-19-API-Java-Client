package org.mithwick.covid19.client.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Covid19APIResponse<T> {
    @JsonProperty("All")
    private T data;
}
