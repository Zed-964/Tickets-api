package com.zed.ticketsapi.controller.rest.models.offer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zed.ticketsapi.constants.ErrorConstants;
import com.zed.ticketsapi.constants.GenericConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Offer {

    @JsonProperty("uuid")
    @Pattern(regexp = GenericConstants.PATTERN_UUID)
    @NotBlank(message = ErrorConstants.UUID_PATTERN_ERROR)
    private String uuid;

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
