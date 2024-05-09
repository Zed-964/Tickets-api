package com.zed.ticketsapi.controller.rest.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ApiError extends Throwable {

    @JsonProperty("code")
    private final HttpStatus code;

    @JsonProperty("message")
    private final String message;
}
