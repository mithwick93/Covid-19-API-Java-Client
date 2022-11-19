package org.mithwick.covid19.client.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Covid19APIResponse<T> {
    @JsonProperty("All")
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Covid19APIResponse{" +
                "data=" + data +
                '}';
    }
}
