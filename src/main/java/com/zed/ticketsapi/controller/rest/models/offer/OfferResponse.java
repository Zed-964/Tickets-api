package com.zed.ticketsapi.controller.rest.models.offer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class OfferResponse {

    @JsonProperty("data")
    private Offer data;
}
