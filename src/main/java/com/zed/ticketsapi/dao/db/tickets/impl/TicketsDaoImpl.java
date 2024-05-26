package com.zed.ticketsapi.dao.db.tickets.impl;

import com.zed.ticketsapi.constants.GenericConstants;
import com.zed.ticketsapi.constants.QueryDbConstants;
import com.zed.ticketsapi.controller.rest.models.tickets.Ticket;
import com.zed.ticketsapi.dao.db.exceptions.DaoException;
import com.zed.ticketsapi.dao.db.tickets.TicketsDao;
import com.zed.ticketsapi.mapper.TicketMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TicketsDaoImpl implements TicketsDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Ticket> getMyTickets(UUID userId) throws DaoException {
        List<Ticket> results;

        String query = QueryDbConstants.SELECT + QueryDbConstants.FROM + QueryDbConstants.TABLE_TICKET +
                QueryDbConstants.INNER_JOIN_TICKET_USER_ID + QueryDbConstants.WHERE + QueryDbConstants.TABLE_USER +
                QueryDbConstants.DOT + QueryDbConstants.USER_ID_EQUAL + QueryDbConstants.QUESTION_MARK;

        try {
            results = jdbcTemplate.query(query, new TicketMapper(), userId);
        } catch (Exception e) {
            log.error(GenericConstants.LOG_QUERY_DAO, e.getMessage(), e.getCause());
            throw new DaoException(GenericConstants.MSG_ERROR_DAO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return results;
    }

    @Override
    public Ticket getTicket(UUID uuid) throws DaoException {
        Ticket result;

        String query = QueryDbConstants.SELECT + QueryDbConstants.FROM + QueryDbConstants.TABLE_TICKET +
                QueryDbConstants.WHERE + QueryDbConstants.TICKET_ID_EQUAL + QueryDbConstants.QUESTION_MARK;

        try {
            result = jdbcTemplate.queryForObject(query, new TicketMapper(), uuid);
        } catch (EmptyResultDataAccessException exception) {
            log.warn("The ticket with uuid {}, is not found !", uuid);
            throw new DaoException("The ticket has not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(GenericConstants.LOG_QUERY_DAO, e.getMessage(), e.getCause());
            throw new DaoException(GenericConstants.MSG_ERROR_DAO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return result;
    }

    @Override
    public void insertNewTicket(Ticket ticket) throws DaoException {
        int result;

        StringBuilder query = new StringBuilder(QueryDbConstants.INSERT_INTO + QueryDbConstants.TABLE_TICKET +
                QueryDbConstants.FORMAT_TICKET + QueryDbConstants.VALUES_5);

        try {
            result = jdbcTemplate.update(query.toString(),
                    ticket.getUuid(), ticket.getFirstname(), ticket.getLastname(), ticket.getDate(), ticket.getUserId());
        } catch (Exception e) {
            log.error(GenericConstants.LOG_QUERY_DAO, e.getMessage(), e.getCause());
            throw new DaoException(GenericConstants.MSG_ERROR_DAO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (result == 0) {
            log.error("Nothing has been created !");
            throw new DaoException("An error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("Insert Tickets Successfully !");
    }

    @Override
    public void deleteTicket(UUID uuid) throws DaoException {
        int result;

        String query = QueryDbConstants.DELETE + QueryDbConstants.FROM + QueryDbConstants.TABLE_TICKET +
                QueryDbConstants.WHERE + QueryDbConstants.TICKET_ID_EQUAL + QueryDbConstants.QUESTION_MARK;
        try {
            result = jdbcTemplate.update(query, uuid);
        } catch (Exception e) {
            log.error(GenericConstants.LOG_QUERY_DAO, e.getMessage(), e.getCause());
            throw new DaoException(GenericConstants.MSG_ERROR_DAO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (result == 0) {
            log.error("Nothing has been deleted !");
            throw new DaoException("Ticket to delete is not found", HttpStatus.NOT_FOUND);
        }

        log.info("Delete Ticket Successfully !");
    }
}