package com.zed.ticketsapi.dao.db.tickets;

import com.zed.ticketsapi.controller.rest.models.tickets.Ticket;
import com.zed.ticketsapi.dao.db.exceptions.DaoException;
import com.zed.ticketsapi.dao.db.tickets.impl.TicketsDaoImpl;
import com.zed.ticketsapi.mapper.TicketMapper;
import com.zed.ticketsapi.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.aot.DisabledInAotMode;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@DisabledInAotMode
class TicketsDaoTest {

    @Mock
    JdbcTemplate jdbcTemplate;

    private TicketsDaoImpl ticketsDao;

    @BeforeEach
    public void setUp() {
        ticketsDao = Mockito.spy(new TicketsDaoImpl(jdbcTemplate));
    }

    @Test
    void getMyTickets() throws DaoException {
        List<Ticket> expected = new ArrayList<>();
        expected.add(TestUtils.createTicket());

        String query = "SELECT * FROM t_tickets INNER JOIN t_users ON t_tickets.idt_usr = t_users.idt_usr WHERE t_users.idt_usr = ?";

        Mockito.when(this.jdbcTemplate.query(Mockito.anyString(), Mockito.any(TicketMapper.class), Mockito.any()))
                .thenReturn(expected);

        var response = ticketsDao.getMyTickets(TestUtils.createUser().getUuid());

        Assertions.assertEquals(expected, response);
        Mockito.verify(jdbcTemplate, Mockito.times(1))
                .query(Mockito.eq(query), Mockito.any(TicketMapper.class), Mockito.any());
    }

    @Test
    void getMyTicketsWithError() {
        String query = "SELECT * FROM t_tickets INNER JOIN t_users ON t_tickets.idt_usr = t_users.idt_usr WHERE t_users.idt_usr = ?";

        Mockito.when(this.jdbcTemplate.query(Mockito.anyString(), Mockito.any(TicketMapper.class), Mockito.any()))
                .thenThrow(new DataAccessException("error") {});

        DaoException error = Assertions.assertThrows(DaoException.class, () -> ticketsDao.getMyTickets(TestUtils.createUser().getUuid()));

        Assertions.assertEquals(TestUtils.createDaoExceptionExecutingQuery().getMessage(), error.getMessage());
        Mockito.verify(jdbcTemplate, Mockito.times(1))
                .query(Mockito.eq(query), Mockito.any(TicketMapper.class), Mockito.any());
    }

    @Test
    void getTicket() throws DaoException {
        Ticket expected = TestUtils.createTicket();
        String query = "SELECT * FROM t_tickets WHERE idt_tck = ?";

        Mockito.when(this.jdbcTemplate.queryForObject(Mockito.anyString(), Mockito.any(TicketMapper.class), Mockito.any()))
                .thenReturn(expected);

        var response = ticketsDao.getTicket(expected.getUuid());

        Assertions.assertEquals(expected, response);
        Mockito.verify(jdbcTemplate, Mockito.times(1))
                .queryForObject(Mockito.eq(query), Mockito.any(TicketMapper.class), Mockito.any());
    }

    @Test
    void getTicketNotFound() {
        String query = "SELECT * FROM t_tickets WHERE idt_tck = ?";

        Mockito.when(this.jdbcTemplate.queryForObject(Mockito.anyString(), Mockito.any(TicketMapper.class), Mockito.any()))
                .thenThrow(new EmptyResultDataAccessException(0));

        DaoException error = Assertions.assertThrows(DaoException.class, () -> ticketsDao.getTicket(TestUtils.createTicket().getUuid()));

        Assertions.assertEquals(TestUtils.createDaoExceptionTicketNotFound().getMessage(), error.getMessage());
        Mockito.verify(jdbcTemplate, Mockito.times(1))
                .queryForObject(Mockito.eq(query), Mockito.any(TicketMapper.class), Mockito.any());
    }

    @Test
    void getTicketWithError() {
        String query = "SELECT * FROM t_tickets WHERE idt_tck = ?";

        Mockito.when(this.jdbcTemplate.queryForObject(Mockito.anyString(), Mockito.any(TicketMapper.class), Mockito.any()))
                .thenThrow(new DataAccessException("error") {});

        DaoException error = Assertions.assertThrows(DaoException.class, () -> ticketsDao.getTicket(TestUtils.createTicket().getUuid()));

        Assertions.assertEquals(TestUtils.createDaoExceptionExecutingQuery().getMessage(), error.getMessage());
        Mockito.verify(jdbcTemplate, Mockito.times(1))
                .queryForObject(Mockito.eq(query), Mockito.any(TicketMapper.class), Mockito.any());
    }

    @Test
    void insertTicket() throws DaoException {
        String query = "INSERT INTO t_tickets (idt_tck, lib_firstname, lib_lastname, tmp, idt_usr) VALUES (?,?,?,?,?)";

        Mockito.when(this.jdbcTemplate.update(Mockito.anyString(), Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.any(), Mockito.any()))
                .thenReturn(1);

        ticketsDao.insertNewTicket(TestUtils.createTicket());

        Mockito.verify(jdbcTemplate, Mockito.times(1))
                .update(Mockito.eq(query), Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.any(), Mockito.any());
    }

    @Test
    void insertTicketWithError() {
        String query = "INSERT INTO t_tickets (idt_tck, lib_firstname, lib_lastname, tmp, idt_usr) VALUES (?,?,?,?,?)";

        Mockito.when(this.jdbcTemplate.update(Mockito.anyString(), Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.any(), Mockito.any()))
                .thenThrow(new DataAccessException("error") {});

        DaoException error = Assertions.assertThrows(DaoException.class, () -> ticketsDao.insertNewTicket(TestUtils.createTicket()));

        Assertions.assertEquals(TestUtils.createDaoExceptionExecutingQuery().getMessage(), error.getMessage());
        Mockito.verify(jdbcTemplate, Mockito.times(1))
                .update(Mockito.eq(query), Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.any(), Mockito.any());
    }

    @Test
    void insertTicketWithResult() {
        String query = "INSERT INTO t_tickets (idt_tck, lib_firstname, lib_lastname, tmp, idt_usr) VALUES (?,?,?,?,?)";

        Mockito.when(this.jdbcTemplate.update(Mockito.anyString(), Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.any(), Mockito.any())).thenReturn(0);

        DaoException error = Assertions.assertThrows(DaoException.class, () -> ticketsDao.insertNewTicket(TestUtils.createTicket()));

        Assertions.assertEquals(TestUtils.createDaoException().getMessage(), error.getMessage());
        Mockito.verify(jdbcTemplate, Mockito.times(1))
                .update(Mockito.eq(query), Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.any(), Mockito.any());
    }

    @Test
    void deleteTicket() throws DaoException {
        String query = "DELETE FROM t_tickets WHERE idt_tck = ?";

        Mockito.when(this.jdbcTemplate.update(Mockito.anyString(), Mockito.any(UUID.class))).thenReturn(1);

        ticketsDao.deleteTicket(TestUtils.createTicket().getUuid());

        Mockito.verify(jdbcTemplate, Mockito.times(1)).update(Mockito.eq(query), Mockito.any(UUID.class));
    }

    @Test
    void deleteTicketWithError() {
        String query = "DELETE FROM t_tickets WHERE idt_tck = ?";

        Mockito.when(this.jdbcTemplate.update(Mockito.anyString(), Mockito.any(UUID.class))).thenThrow(new DataAccessException("error") {});

        DaoException error = Assertions.assertThrows(DaoException.class, () -> ticketsDao.deleteTicket(TestUtils.createTicket().getUuid()));

        Assertions.assertEquals(TestUtils.createDaoExceptionExecutingQuery().getMessage(), error.getMessage());
        Mockito.verify(jdbcTemplate, Mockito.times(1)).update(Mockito.eq(query), Mockito.any(UUID.class));
    }

    @Test
    void deleteTicketNotFound() {
        String query = "DELETE FROM t_tickets WHERE idt_tck = ?";

        Mockito.when(this.jdbcTemplate.update(Mockito.anyString(), Mockito.any(UUID.class))).thenReturn(0);

        DaoException error = Assertions.assertThrows(DaoException.class, () -> ticketsDao.deleteTicket(TestUtils.createTicket().getUuid()));

        Assertions.assertEquals(TestUtils.createDaoExceptionTicketToDeleteNotFound().getMessage(), error.getMessage());
        Mockito.verify(jdbcTemplate, Mockito.times(1)).update(Mockito.eq(query), Mockito.any(UUID.class));
    }
}