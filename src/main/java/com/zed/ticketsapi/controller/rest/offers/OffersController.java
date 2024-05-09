package com.zed.ticketsapi.controller.rest.offers;

import com.zed.ticketsapi.constants.GenericConstants;
import com.zed.ticketsapi.constants.PathConstants;
import com.zed.ticketsapi.controller.rest.models.ApiErrorResponse;
import com.zed.ticketsapi.controller.rest.models.ApiTicketsResponse;
import com.zed.ticketsapi.controller.rest.models.offer.Offer;
import com.zed.ticketsapi.controller.rest.models.offer.OfferResponse;
import com.zed.ticketsapi.controller.rest.models.offer.OfferSimple;
import com.zed.ticketsapi.controller.rest.models.offer.OffersResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface OffersController {
    @Operation(summary = "Get all offers from tickets",
               description = "Get all offers from tickets",
               tags = { GenericConstants.OFFER_TAG })

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "successful operation",
                         content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = OffersResponse.class))) })

    @GetMapping(value = PathConstants.OFFERS_PATH, produces = { "application/json" })
    ResponseEntity<ApiTicketsResponse> getOffers();

    @Operation(summary = "Update offer",
               description = "Update offer of ticket",
               tags = { GenericConstants.OFFER_TAG })

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "Successful operation",
                         content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = OfferResponse.class))),

            @ApiResponse(responseCode = "400",
                         description = "Invalid request",
                         content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = ApiErrorResponse.class))),

            @ApiResponse(responseCode = "401",
                         description = "Invalid Authentification",
                         content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = ApiErrorResponse.class))),

            @ApiResponse(responseCode = "404",
                         description = "Offer not found",
                         content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = ApiErrorResponse.class))) })

    @PutMapping(value = PathConstants.OFFER_ID_PATH,
                produces = { "application/json" },
                consumes = { "application/json" })
    @SecurityRequirement(name = GenericConstants.BEARER)
    @PreAuthorize("hasRole('client-api-admin')")
    ResponseEntity<ApiTicketsResponse> updateOffer(@NotBlank @PathVariable String offerId,
                                              @Valid @RequestBody Offer updatedOffer);

    @Operation(summary = "Create a new offer",
               description = "Create a new offer of ticket",
               tags={ GenericConstants.OFFER_TAG })

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                         description = "Successful operation",
                         content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = OfferResponse.class))),

            @ApiResponse(responseCode = "400",
                         description = "Invalid request",
                         content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = ApiErrorResponse.class))),

            @ApiResponse(responseCode = "401",
                         description = "Invalid Authentification",
                         content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = ApiErrorResponse.class))) })

    @PostMapping(value =  PathConstants.OFFERS_PATH,
            produces = { "application/json" },
            consumes = { "application/json" })
    @SecurityRequirement(name = GenericConstants.BEARER)
    @PreAuthorize("hasRole('client-api-admin')")
            ResponseEntity<ApiTicketsResponse> createOffer(@Valid @RequestBody OfferSimple newOffer);

    @Operation(summary = "Deletes a offer",
               description = "Delete a offer",
               tags={ GenericConstants.OFFER_TAG })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                         description = "Successful operation"),

            @ApiResponse(responseCode = "400",
                         description = "Invalid request",
                         content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = ApiErrorResponse.class))),

            @ApiResponse(responseCode = "401",
                         description = "Invalid Authentification",
                         content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = ApiErrorResponse.class))),

            @ApiResponse(responseCode = "404",
                         description = "Offer not found",
                         content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = ApiErrorResponse.class))) })

    @DeleteMapping(value = PathConstants.OFFER_ID_PATH)
    @SecurityRequirement(name = GenericConstants.BEARER)
    @PreAuthorize("hasRole('client-api-admin')")
    ResponseEntity<ApiTicketsResponse> deleteOffer(@NotBlank @PathVariable String offerId);
}
