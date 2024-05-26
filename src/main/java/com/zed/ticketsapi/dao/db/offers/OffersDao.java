package com.zed.ticketsapi.dao.db.offers;

import com.zed.ticketsapi.controller.rest.models.offers.Offer;
import com.zed.ticketsapi.dao.db.exceptions.DaoException;

import java.util.List;
import java.util.UUID;

public interface OffersDao {
    List<Offer> getAllOffer() throws DaoException;

    Offer getOffer(UUID uuid) throws DaoException;

    void insertNewOffer(Offer offer) throws DaoException;

    void updateOffer(Offer offer) throws DaoException;

    void deleteOffer(UUID uuid) throws DaoException;
}
