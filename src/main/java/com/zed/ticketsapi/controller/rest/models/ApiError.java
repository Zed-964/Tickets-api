package com.zed.ticketsapi.controller.rest.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiError {

    @JsonProperty("code")
    private String code;

    @JsonProperty("message")
    private String message;
}
