package com.zed.ticketsapi.services.offers.impl;

import com.zed.ticketsapi.controller.rest.models.ApiError;
import com.zed.ticketsapi.controller.rest.models.offer.Offer;
import com.zed.ticketsapi.controller.rest.models.offer.OfferResponse;
import com.zed.ticketsapi.controller.rest.models.offer.OfferSimple;
import com.zed.ticketsapi.controller.rest.models.offer.OffersResponse;
import com.zed.ticketsapi.dao.db.exceptions.DaoException;
import com.zed.ticketsapi.dao.db.offers.OffersDao;
import com.zed.ticketsapi.services.offers.OffersServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OffersServicesImpl implements OffersServices {

    private final OffersDao offersDao;

    @Override
    public OfferResponse create(OfferSimple offer) throws ApiError {
        Offer result;

        UUID uuidGenerated = UUID.randomUUID();

        Offer newOffer = Offer.builder()
                .uuid(uuidGenerated)
                .name(offer.getName())
                .description(offer.getDescription())
                .numberTickets(offer.getNumberTickets())
                .price(offer.getPrice())
                .build();
        try {
            offersDao.insertNewOffer(newOffer);
            result = offersDao.getOffer(uuidGenerated);
        } catch (DaoException e) {
            log.error("Error occurred when create new offer. Error : {}, code : {}", e.getMessage(), e.getCode());
            throw ApiError.builder().message("An error occurred when create new offer.").code(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return OfferResponse.builder().data(result).build();
    }

    @Override
    public OfferResponse update(UUID uuid,Offer offerUpdated) throws ApiError {
        Offer result;

        try {
            offersDao.getOffer(uuid);
        } catch (DaoException e) {
            if (e.getCode() == HttpStatus.NOT_FOUND) {
                log.error("Update cancel, the offer is not found");
                throw ApiError.builder().message("The offer to update is not found").code(e.getCode()).build();
            }
            log.error("Error occurred when update a offer. Error : {}, code : {}", e.getMessage(), e.getCode());
            throw ApiError.builder().message("An error occurred when update a offer.").code(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        try {
            offersDao.updateOffer(offerUpdated);
            result = offersDao.getOffer(uuid);
        } catch (DaoException e) {
            log.error("Error occurred when update a offer. Error : {}, code : {}", e.getMessage(), e.getCode());
            throw ApiError.builder().message("An error occurred when update a offer.").code(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return OfferResponse.builder().data(result).build();
    }

    @Override
    public OffersResponse allOffers() throws ApiError {
        List<Offer> results;

        try {
            results = offersDao.getAllOffer();
        } catch (DaoException e) {
            if (e.getCode() ==  HttpStatus.NOT_FOUND) {
                throw ApiError.builder().message(e.getMessage()).code(e.getCode()).build();
            }
            log.error("Error occurred when get all offer. Error : {}, code : {}", e.getMessage(), e.getCode());
            throw ApiError.builder().message("An error occurred when get all offer.").code(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return OffersResponse.builder().data(results).build();
    }

    @Override
    public void delete(UUID uuid) throws ApiError {

        try {
            offersDao.getOffer(uuid);
        } catch (DaoException e) {
            if (e.getCode() == HttpStatus.NOT_FOUND) {
                log.error("Delete cancel, the offer is not found");
                throw ApiError.builder().message("The offer to delete is not found").code(e.getCode()).build();
            }
            log.error("Error occurred when delete a offer. Error : {}, code : {}", e.getMessage(), e.getCode());
            throw ApiError.builder().message("An error occurred when delete a offer.").code(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        try {
            offersDao.deleteOffer(uuid);
        } catch (DaoException e) {
            log.error("Error occurred when delete a offer. Error : {}, code : {}", e.getMessage(), e.getCode());
            throw ApiError.builder().message("An error occurred when delete a offer.").code(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}
