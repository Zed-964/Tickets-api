package com.zed.ticketsapi.controller.rest.offers.impl;

import com.zed.ticketsapi.constants.GenericConstants;
import com.zed.ticketsapi.constants.PathConstants;
import com.zed.ticketsapi.controller.rest.models.offer.Offer;
import com.zed.ticketsapi.controller.rest.models.offer.OfferResponse;
import com.zed.ticketsapi.controller.rest.models.offer.OfferSimple;
import com.zed.ticketsapi.controller.rest.offers.OffersController;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping(PathConstants.CONTROLLER_V1_PATH)
@Tag(name = GenericConstants.OFFER_TAG, description = GenericConstants.OFFER_DESCRIPTION)
@SecurityRequirement(name = GenericConstants.BEARER)
public class OffersControllerImpl implements OffersController {

    @Override
    public ResponseEntity<String> getOffers() {
        return null;
    }

    @Override
    public ResponseEntity<OfferResponse> updateOffer(String offerId, Offer updatedOffer) {
        return null;
    }

    @Override
    public ResponseEntity<OfferResponse> createOffer(OfferSimple newOffer) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteOffer(String offerId) {
        return null;
    }
}
