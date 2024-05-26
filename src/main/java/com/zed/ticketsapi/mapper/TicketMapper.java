package com.zed.ticketsapi.mapper;

import com.zed.ticketsapi.constants.QueryDbConstants;
import com.zed.ticketsapi.controller.rest.models.tickets.Ticket;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class TicketMapper implements RowMapper<Ticket> {
    @Override
    public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Ticket.builder()
                .uuid(UUID.fromString(rs.getString(QueryDbConstants.TICKET_ID)))
                .firstname(rs.getString(QueryDbConstants.TICKET_FIRSTNAME))
                .lastname(rs.getString(QueryDbConstants.TICKET_LASTNAME))
                .date(rs.getDate(QueryDbConstants.TICKET_DATE).toLocalDate())
                .userId(UUID.fromString(rs.getString(QueryDbConstants.TICKET_USER_ID)))
                .build();
    }
}