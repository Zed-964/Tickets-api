package com.zed.ticketsapi.controller.rest.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Card {

    @JsonProperty("number")
    private final String number;

    @JsonProperty("holderName")
    private final String holderName;

    @JsonProperty("expirationDate")
    private final String expirationDate;

    @JsonProperty("securityCode")
    private final String securityCode;
}
