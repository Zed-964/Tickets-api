package com.zed.ticketsapi.controller.rest.models.offers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zed.ticketsapi.controller.rest.models.ApiTicketsResponse;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class OfferResponse implements ApiTicketsResponse {

    @JsonProperty("data")
    private Offer data;

    @Override
    public Object data() {
        return data;
    }
}