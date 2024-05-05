package com.zed.ticketsapi.controller.rest.models.ticket;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TicketsResponse {

    @JsonProperty("data")
    private List<Ticket> data;
}
