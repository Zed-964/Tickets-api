package com.zed.ticketsapi.controller.rest.tickets.impl;

import com.zed.ticketsapi.constants.GenericConstants;
import com.zed.ticketsapi.constants.PathConstants;
import com.zed.ticketsapi.controller.rest.models.ticket.Ticket;
import com.zed.ticketsapi.controller.rest.models.ticket.TicketSimple;
import com.zed.ticketsapi.controller.rest.models.ticket.TicketsResponse;
import com.zed.ticketsapi.controller.rest.tickets.TicketsController;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping(PathConstants.CONTROLLER_V1_PATH)
@Tag(name = GenericConstants.TICKET_TAG, description = GenericConstants.TICKET_DESCRIPTION)
@SecurityRequirement(name = GenericConstants.BEARER)
public class TicketsControllerImpl implements TicketsController {
    @Override
    public ResponseEntity<TicketsResponse> getTicketsFromUser() {
        return null;
    }

    @Override
    public ResponseEntity<Ticket> postPaymentTicket(List<TicketSimple> newTicket) {
        return null;
    }
}
