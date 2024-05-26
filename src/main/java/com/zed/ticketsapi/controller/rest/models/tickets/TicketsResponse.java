package com.zed.ticketsapi.controller.rest.models.tickets;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zed.ticketsapi.controller.rest.models.ApiTicketsResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TicketsResponse implements ApiTicketsResponse {

    @JsonProperty("data")
    private List<Ticket> data;

    @Override
    public Object data() {
        return data;
    }
}
