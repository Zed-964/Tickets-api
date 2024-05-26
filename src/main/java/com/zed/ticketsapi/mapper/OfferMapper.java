package com.zed.ticketsapi.mapper;

import com.zed.ticketsapi.constants.QueryDbConstants;
import com.zed.ticketsapi.controller.rest.models.offers.Offer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class OfferMapper implements RowMapper<Offer> {
    @Override
    public Offer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Offer.builder()
                .uuid(UUID.fromString(rs.getString(QueryDbConstants.OFFER_ID)))
                .name(rs.getString(QueryDbConstants.OFFER_NAME))
                .description(rs.getString(QueryDbConstants.OFFER_DESCRIPTION))
                .numberTickets(rs.getInt(QueryDbConstants.OFFER_NUMBER_TICKETS))
                .price(rs.getFloat(QueryDbConstants.OFFER_PRICE))
                .build();
    }
}
