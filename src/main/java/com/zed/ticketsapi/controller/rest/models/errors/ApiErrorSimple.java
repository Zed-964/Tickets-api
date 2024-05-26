package com.zed.ticketsapi.controller.rest.models.errors;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class ApiErrorSimple {

    @JsonProperty("code")
    private final HttpStatus code;

    @JsonProperty("message")
    private final String message;

    public ApiErrorSimple(ApiError e) {
        this.message = e.getMessage();
        this.code = e.getCode();
    }
}
