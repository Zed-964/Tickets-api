package com.zed.ticketsapi.controller.rest.models.offer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zed.ticketsapi.constants.ErrorsConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OfferSimple {

    @JsonProperty("name")
    @NotBlank(message = ErrorsConstants.NAME_EMPTY)
    private String name;

    @JsonProperty("description")
    @NotBlank(message = ErrorsConstants.DESCRIPTION_EMPTY)
    private String description;

    @JsonProperty("numberTickets")
    @NotNull(message = ErrorsConstants.NUMBER_TICKETS_EMPTY)
    private int numberTickets;

    @JsonProperty("price")
    @NotNull(message = ErrorsConstants.PRICE_EMPTY)
    private float price;
}