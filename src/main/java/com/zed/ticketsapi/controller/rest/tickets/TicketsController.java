package com.zed.ticketsapi.controller.rest.tickets;

import com.zed.ticketsapi.constants.GenericConstants;
import com.zed.ticketsapi.constants.PathConstants;
import com.zed.ticketsapi.controller.rest.models.ApiTicketsResponse;
import com.zed.ticketsapi.controller.rest.models.errors.ApiErrorResponse;
import com.zed.ticketsapi.controller.rest.models.tickets.Ticket;
import com.zed.ticketsapi.controller.rest.models.tickets.TicketsPayment;
import com.zed.ticketsapi.controller.rest.models.tickets.TicketsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface TicketsController {

    @Operation(summary = "Get all tickets from authenticated user",
               description = "Get all tickets from authenticated user",
               tags={GenericConstants.TICKET_TAG})

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "successful operation",
                         content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = TicketsResponse.class))),

            @ApiResponse(responseCode = "401",
                         description = "Invalid Authentification",
                         content = @Content(mediaType = "application/json")) })

    @GetMapping(value = PathConstants.TICKETS_ME_PATH,
            produces = { "application/json" })
    ResponseEntity<ApiTicketsResponse> getTicketsFromUser();


    @Operation(summary = "Pay a new ticket",
               description = "Pay a new ticket and attach to the user authenticated",
               tags={ GenericConstants.TICKET_TAG })

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                         description = "Successful operation",
                         content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = Ticket.class))),

            @ApiResponse(responseCode = "400",
                         description = "Invalid request",
                         content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = ApiErrorResponse.class))),

            @ApiResponse(responseCode = "401",
                         description = "Invalid Authentification",
                         content = @Content(mediaType = "application/json")) })

    @PostMapping(value = PathConstants.TICKETS_PAYMENT_PATH,
            produces = { "application/json" },
            consumes = { "application/json" })
    ResponseEntity<ApiTicketsResponse> postPaymentTicket(@Valid @RequestBody TicketsPayment ticketPayment);
}
