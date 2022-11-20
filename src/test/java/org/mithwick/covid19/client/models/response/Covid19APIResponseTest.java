package org.mithwick.covid19.client.models.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Covid19APIResponseTest {

    @Test
    public void Covid19APIResponse_createWrapper() {
        String sampleString = "Covid19APIResponse Test data";

        Covid19APIResponse<String> stringCovid19APIResponse = new Covid19APIResponse<>();
        stringCovid19APIResponse.setData(sampleString);

        assertEquals(sampleString, stringCovid19APIResponse.getData());
    }

}