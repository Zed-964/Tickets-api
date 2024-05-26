package com.zed.ticketsapi.controller.rest.models.tickets;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zed.ticketsapi.controller.rest.models.ApiTicketsResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TicketResponse implements ApiTicketsResponse {

    @JsonProperty("data")
    private Ticket data;

    @Override
    public Object data() {
        return data;
    }
}