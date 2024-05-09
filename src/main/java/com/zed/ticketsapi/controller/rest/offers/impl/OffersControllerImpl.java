package com.zed.ticketsapi.controller.rest.offers.impl;

import com.zed.ticketsapi.constants.GenericConstants;
import com.zed.ticketsapi.constants.PathConstants;
import com.zed.ticketsapi.controller.rest.models.ApiError;
import com.zed.ticketsapi.controller.rest.models.ApiErrorResponse;
import com.zed.ticketsapi.controller.rest.models.ApiErrorSimple;
import com.zed.ticketsapi.controller.rest.models.ApiTicketsResponse;
import com.zed.ticketsapi.controller.rest.models.offer.Offer;
import com.zed.ticketsapi.controller.rest.models.offer.OfferResponse;
import com.zed.ticketsapi.controller.rest.models.offer.OfferSimple;
import com.zed.ticketsapi.controller.rest.models.offer.OffersResponse;
import com.zed.ticketsapi.controller.rest.offers.OffersController;
import com.zed.ticketsapi.controller.utils.ErrorUtils;
import com.zed.ticketsapi.services.offers.OffersServices;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@Slf4j
@Validated
@RestController
@RequestMapping(PathConstants.CONTROLLER_V1_PATH)
@Tag(name = GenericConstants.OFFER_TAG, description = GenericConstants.OFFER_DESCRIPTION)
@SecurityRequirement(name = GenericConstants.BEARER)
@RequiredArgsConstructor
public class OffersControllerImpl implements OffersController {

    public final OffersServices offersServices;

    @Override
    public ResponseEntity<ApiTicketsResponse> getOffers() {
        OffersResponse response;

        try {
            response = offersServices.allOffers();
        } catch (ApiError e) {
            return ErrorUtils.thrownApiError(e);
        }

        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<ApiTicketsResponse> updateOffer(String offerId, Offer updatedOffer) {
        OfferResponse response;

        try {
            if (offerId.length() != 36) {
                return ErrorUtils.thrownFormatUuidInvalid();
            }

            UUID uuid = UUID.fromString(offerId);

            if (!offerId.equals(updatedOffer.getUuid().toString())) {

                ApiErrorResponse errorResponse;
                ApiError e;

                e = ApiError.builder().message("You can't change the uuid").code(HttpStatus.BAD_REQUEST).build();
                errorResponse = ApiErrorResponse.builder().data(new ApiErrorSimple(e)).build();

                return new ResponseEntity<>(errorResponse, e.getCode());
            }

            response = offersServices.update(uuid, updatedOffer);
        } catch (ApiError e) {
            return ErrorUtils.thrownApiError(e);

        } catch (IllegalArgumentException e) {
            log.error("Format uuid invalid, error : {}", e.getMessage());
            return ErrorUtils.thrownFormatUuidInvalid();
        }

        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<ApiTicketsResponse> createOffer(OfferSimple newOffer) {
        ApiTicketsResponse response;

        try {
            response =offersServices.create(newOffer);
        } catch (ApiError e) {
            return ErrorUtils.thrownApiError(e);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<ApiTicketsResponse> deleteOffer(String offerId) {
        try {
            if (offerId.length() != 36) {
                return ErrorUtils.thrownFormatUuidInvalid();
            }

            UUID uuid = UUID.fromString(offerId);
            offersServices.delete(uuid);

        } catch (ApiError e) {
            return ErrorUtils.thrownApiError(e);

        } catch (IllegalArgumentException e) {
            log.error("Format uuid invalid, error : {}", e.getMessage());
            return ErrorUtils.thrownFormatUuidInvalid();
        }

        return ResponseEntity.noContent().build();
    }
}
