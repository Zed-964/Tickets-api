package com.zed.ticketsapi.controller.rest.models.tickets;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zed.ticketsapi.controller.rest.models.Card;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class TicketsPayment {

    @JsonProperty("card")
    private final Card card;

    @JsonProperty("mount")
    private final double mount;

    @JsonProperty("status")
    private String status;

    @JsonProperty("tickets")
    private final List<TicketSimple> tickets;
}
