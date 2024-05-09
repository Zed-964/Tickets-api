package com.zed.ticketsapi.controller.rest.models;

import com.fasterxml.jackson.annotation.JsonProperty;
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
