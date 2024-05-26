package com.zed.ticketsapi.services.tickets;

import com.zed.ticketsapi.controller.rest.models.errors.ApiError;
import com.zed.ticketsapi.controller.rest.models.tickets.TicketsPayment;
import com.zed.ticketsapi.controller.rest.models.tickets.TicketSimple;
import com.zed.ticketsapi.controller.rest.models.tickets.TicketsResponse;

import java.util.List;
import java.util.UUID;

public interface TicketsServices {

    TicketsResponse payment(TicketsPayment ticketPayment) throws ApiError;

    TicketsResponse create(List<TicketSimple> ticketsToCreate, List<UUID> uuids) throws ApiError;

    TicketsResponse myTickets() throws ApiError;

    void delete(UUID uuid) throws ApiError;
}
