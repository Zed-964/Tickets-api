package com.zed.ticketsapi.controller.rest.models.offer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OffersResponse {

    @JsonProperty("data")
    private List<Offer> data;
}
