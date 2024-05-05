package com.zed.ticketsapi.controller.rest.tickets;

import com.zed.ticketsapi.constants.GenericConstants;
import com.zed.ticketsapi.constants.PathConstants;
import com.zed.ticketsapi.controller.rest.models.ApiError;
import com.zed.ticketsapi.controller.rest.models.ticket.Ticket;
import com.zed.ticketsapi.controller.rest.models.ticket.TicketSimple;
import com.zed.ticketsapi.controller.rest.models.ticket.TicketsResponse;
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

import java.util.List;

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
                         content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = ApiError.class))) })

    @GetMapping(value = PathConstants.TICKETS_ME_PATH,
            produces = { "application/json" })
    ResponseEntity<TicketsResponse> getTicketsFromUser();


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
                                 schema = @Schema(implementation = ApiError.class))),

            @ApiResponse(responseCode = "401",
                         description = "Invalid Authentification",
                         content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = ApiError.class))) })

    @PostMapping(value = PathConstants.TICKETS_PAYMENT_PATH,
            produces = { "application/json" },
            consumes = { "application/json" })
    ResponseEntity<Ticket> postPaymentTicket(@Valid @RequestBody List<TicketSimple> newTicket);
}
