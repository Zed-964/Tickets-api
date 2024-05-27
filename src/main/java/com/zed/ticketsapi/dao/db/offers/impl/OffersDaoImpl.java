package com.zed.ticketsapi.dao.db.offers.impl;

import com.zed.ticketsapi.constants.GenericConstants;
import com.zed.ticketsapi.constants.QueryDbConstants;
import com.zed.ticketsapi.controller.rest.models.offers.Offer;
import com.zed.ticketsapi.dao.db.exceptions.DaoException;
import com.zed.ticketsapi.dao.db.offers.OffersDao;
import com.zed.ticketsapi.mapper.OfferMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class OffersDaoImpl implements OffersDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Offer> getAllOffer() throws DaoException {
        List<Offer> results;

        String query = QueryDbConstants.SELECT + QueryDbConstants.FROM + QueryDbConstants.TABLE_OFFER;

        try {
            results = jdbcTemplate.query(query, new OfferMapper());
        } catch (Exception e) {
            log.error(GenericConstants.LOG_QUERY_DAO, e.getMessage(), e.getCause());
            throw new DaoException(GenericConstants.MSG_ERROR_DAO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (results.isEmpty()) {
            log.error("No offers has been found !");
            throw new DaoException("None of the offers has been found", HttpStatus.NOT_FOUND);
        }

        return results;
    }

    @Override
    public Offer getOffer(UUID uuid) throws DaoException {
        Offer result;

        String query = QueryDbConstants.SELECT + QueryDbConstants.FROM + QueryDbConstants.TABLE_OFFER +
                QueryDbConstants.WHERE + QueryDbConstants.OFFER_ID_EQUAL + QueryDbConstants.QUESTION_MARK;

        try {
            result = jdbcTemplate.queryForObject(query, new OfferMapper(), uuid);
        } catch (Exception e) {
            log.error(GenericConstants.LOG_QUERY_DAO, e.getMessage(), e.getCause());
            throw new DaoException(GenericConstants.MSG_ERROR_DAO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (result == null) {
            log.error("The offer with uuid {}, is not found !", uuid);
            throw new DaoException("The offer has not found", HttpStatus.NOT_FOUND);
        }

        return result;
    }

    @Override
    public void insertNewOffer(Offer offer) throws DaoException {
        int result;

        String query = QueryDbConstants.INSERT_INTO + QueryDbConstants.TABLE_OFFER + QueryDbConstants.FORMAT_OFFER
                + QueryDbConstants.VALUES_5;

        try {
            result = jdbcTemplate.update(query,
                    offer.getUuid(), offer.getName(), offer.getDescription(), offer.getNumberTickets(), offer.getPrice());
        } catch (Exception e) {
            log.error(GenericConstants.LOG_QUERY_DAO, e.getMessage(), e.getCause());
            throw new DaoException(GenericConstants.MSG_ERROR_DAO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (result == 0) {
            log.error("Nothing has been created !");
            throw new DaoException("An error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("Insert Offer Successfully !");
    }

    @Override
    public void updateOffer(Offer offer) throws DaoException {
        int result;

        String query = QueryDbConstants.UPDATE + QueryDbConstants.TABLE_OFFER + QueryDbConstants.SET +
                QueryDbConstants.OFFER_NAME_EQUAL + QueryDbConstants.QUESTION_MARK + QueryDbConstants.COMA +
                QueryDbConstants.OFFER_DESCRIPTION_EQUAL + QueryDbConstants.QUESTION_MARK + QueryDbConstants.COMA +
                QueryDbConstants.OFFER_NUMBER_TICKETS_EQUAL + QueryDbConstants.QUESTION_MARK + QueryDbConstants.COMA +
                QueryDbConstants.OFFER_PRICE_EQUAL + QueryDbConstants.QUESTION_MARK +
                QueryDbConstants.WHERE + QueryDbConstants.OFFER_ID_EQUAL + QueryDbConstants.QUESTION_MARK;

        try {
            result = jdbcTemplate.update(query,
                    offer.getName(), offer.getDescription(), offer.getNumberTickets(), offer.getPrice(), offer.getUuid());
        } catch (Exception e) {
            log.error(GenericConstants.LOG_QUERY_DAO, e.getMessage(), e.getCause());
            throw new DaoException(GenericConstants.MSG_ERROR_DAO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (result == 0) {
            log.error("Nothing has been Updated !");
            throw new DaoException("Offer to update is not found", HttpStatus.NOT_FOUND);
        }

        log.info("Update Offer Successfully !");
    }

    @Override
    public void deleteOffer(UUID uuid) throws DaoException {
        int result;

        String query = QueryDbConstants.DELETE + QueryDbConstants.FROM + QueryDbConstants.TABLE_OFFER +
                QueryDbConstants.WHERE + QueryDbConstants.OFFER_ID_EQUAL + QueryDbConstants.QUESTION_MARK;
        try {
            result = jdbcTemplate.update(query, uuid);
        } catch (Exception e) {
            log.error(GenericConstants.LOG_QUERY_DAO, e.getMessage(), e.getCause());
            throw new DaoException(GenericConstants.MSG_ERROR_DAO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (result == 0) {
            log.error("Nothing has been deleted !");
            throw new DaoException("Offer to delete is not found", HttpStatus.NOT_FOUND);
        }

        log.info("Delete Offer Successfully !");
    }
}
