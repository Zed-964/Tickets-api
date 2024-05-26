package com.zed.ticketsapi.dao.db.tickets;

import com.zed.ticketsapi.controller.rest.models.tickets.Ticket;
import com.zed.ticketsapi.dao.db.exceptions.DaoException;

import java.util.List;
import java.util.UUID;

public interface TicketsDao {
    List<Ticket> getMyTickets(UUID userUuid) throws DaoException;

    Ticket getTicket(UUID uuid) throws DaoException;

    void insertNewTicket(Ticket ticket) throws DaoException;

    void deleteTicket(UUID uuid) throws DaoException;
}
