package com.zed.ticketsapi.controller.rest.models.offer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zed.ticketsapi.controller.rest.models.ApiTicketsResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OffersResponse implements ApiTicketsResponse {

    @JsonProperty("data")
    private List<Offer> data;

    @Override
    public Object data() {
        return data;
    }
}
