package com.zed.ticketsapi.controller.rest.models.offer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zed.ticketsapi.constants.ErrorConstants;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OfferSimple {

    @JsonProperty("name")
    @NotBlank(message = ErrorConstants.NAME_EMPTY)
    private String name;

    @JsonProperty("description")
    @NotBlank(message = ErrorConstants.DESCRIPTION_EMPTY)
    private String description;

    @JsonProperty("numberTickets")
    @NotBlank(message = ErrorConstants.NUMBER_TICKETS_EMPTY)
    private int numberTickets;

    @JsonProperty("price")
    @NotBlank(message = ErrorConstants.PRICE_EMPTY)
    private float price;
}
