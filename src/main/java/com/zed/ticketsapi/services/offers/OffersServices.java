package com.zed.ticketsapi.services.offers;

import com.zed.ticketsapi.controller.rest.models.ApiError;
import com.zed.ticketsapi.controller.rest.models.offer.Offer;
import com.zed.ticketsapi.controller.rest.models.offer.OfferResponse;
import com.zed.ticketsapi.controller.rest.models.offer.OfferSimple;
import com.zed.ticketsapi.controller.rest.models.offer.OffersResponse;

import java.util.UUID;

public interface OffersServices {

     OfferResponse create(OfferSimple offerToCreated) throws ApiError;

     OfferResponse update(UUID uuid, Offer offerUpdated) throws ApiError;

     OffersResponse allOffers() throws ApiError;

     void delete(UUID uuid) throws ApiError;
}
