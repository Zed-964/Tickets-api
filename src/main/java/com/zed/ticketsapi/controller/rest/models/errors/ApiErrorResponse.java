package com.zed.ticketsapi.controller.rest.models.errors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zed.ticketsapi.controller.rest.models.ApiTicketsResponse;
import lombok.Builder;

@Builder
public class ApiErrorResponse implements ApiTicketsResponse {

    @JsonProperty("data")
    private ApiErrorSimple data;

    @Override
    public Object data() {
        return data;
    }
}
