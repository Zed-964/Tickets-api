package com.zed.ticketsapi.controller.rest.tickets.impl;

import com.zed.ticketsapi.constants.GenericConstants;
import com.zed.ticketsapi.controller.rest.models.ApiTicketsResponse;
import com.zed.ticketsapi.controller.rest.models.errors.ApiError;
import com.zed.ticketsapi.controller.rest.models.tickets.TicketsPayment;
import com.zed.ticketsapi.controller.rest.models.tickets.TicketsResponse;
import com.zed.ticketsapi.controller.rest.tickets.TicketsController;
import com.zed.ticketsapi.controller.utils.ErrorUtils;
import com.zed.ticketsapi.services.tickets.TicketsServices;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;


@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = GenericConstants.TICKET_TAG, description = GenericConstants.TICKET_DESCRIPTION)
@SecurityRequirement(name = GenericConstants.BEARER)
public class TicketsControllerImpl implements TicketsController {

    private final TicketsServices ticketsServices;

    @Override
    public ResponseEntity<ApiTicketsResponse> getTicketsFromUser() {
        TicketsResponse response;

        try {
            response = ticketsServices.myTickets();
        } catch (ApiError e) {
            return ErrorUtils.thrownApiError(e);
        }

        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<ApiTicketsResponse> postPaymentTicket(TicketsPayment ticketPayment) {
        ApiTicketsResponse response;

        try {
            response = ticketsServices.payment(ticketPayment);
        } catch (ApiError e) {
            return ErrorUtils.thrownApiError(e);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
