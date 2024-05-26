package com.zed.ticketsapi.services.offers;

import com.zed.ticketsapi.controller.rest.models.errors.ApiError;
import com.zed.ticketsapi.controller.rest.models.offers.Offer;
import com.zed.ticketsapi.controller.rest.models.offers.OfferResponse;
import com.zed.ticketsapi.controller.rest.models.offers.OfferSimple;
import com.zed.ticketsapi.controller.rest.models.offers.OffersResponse;

import java.util.UUID;

public interface OffersServices {

     OfferResponse create(OfferSimple offerToCreated) throws ApiError;

     OfferResponse update(UUID uuid, Offer offerUpdated) throws ApiError;

     OffersResponse allOffers() throws ApiError;

     void delete(UUID uuid) throws ApiError;
}