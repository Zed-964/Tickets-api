package com.zed.ticketsapi.services.tickets;

import com.zed.ticketsapi.controller.rest.models.errors.ApiError;
import com.zed.ticketsapi.controller.rest.models.tickets.Ticket;
import com.zed.ticketsapi.controller.rest.models.tickets.TicketsResponse;
import com.zed.ticketsapi.dao.db.exceptions.DaoException;
import com.zed.ticketsapi.dao.db.tickets.TicketsDao;
import com.zed.ticketsapi.dao.db.users.UsersDao;
import com.zed.ticketsapi.services.jwt.JwtUserDetailsServices;
import com.zed.ticketsapi.services.tickets.impl.TicketsServicesImpl;
import com.zed.ticketsapi.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.aot.DisabledInAotMode;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@DisabledInAotMode
class TicketsServicesTest {

    @Mock
    private TicketsDao ticketsDao;

    @Mock
    private JwtUserDetailsServices jwtUserDetailsServices;

    @Mock
    private UsersDao usersDao;

    @InjectMocks
    TicketsServicesImpl ticketsServices;

    @Test
    void createTicketsWithUserExist() throws DaoException, ApiError {
        Ticket expected = TestUtils.createTicket();

        Mockito.when(jwtUserDetailsServices.getUuidUser()).thenReturn(UUID.fromString("31347b9a-542e-4f48-a7d2-3c30c330ccc0"));
        Mockito.when(jwtUserDetailsServices.getEmailUser()).thenReturn("basic.simple@email.com");

        Mockito.when(usersDao.getUser(Mockito.any())).thenReturn(TestUtils.createUser());
        Mockito.doNothing().when(ticketsDao).insertNewTicket(Mockito.any());
        Mockito.when(ticketsDao.getTicket(Mockito.any())).thenReturn(expected);

        TicketsResponse response = ticketsServices.create(TestUtils.createTicketsSimple(), new ArrayList<>());

        Assertions.assertEquals(expected, response.getData().get(0));
        Mockito.verify(jwtUserDetailsServices, Mockito.times(1)).getUuidUser();
        Mockito.verify(jwtUserDetailsServices, Mockito.times(1)).getEmailUser();
        Mockito.verify(usersDao, Mockito.times(1)).getUser(Mockito.any());
        Mockito.verify(ticketsDao, Mockito.times(1)).insertNewTicket(Mockito.any());
        Mockito.verify(ticketsDao, Mockito.times(1)).getTicket(Mockito.any());

    }

    @Test
    void createTicketsWithUserNoExist() throws DaoException, ApiError {
        Ticket expected = TestUtils.createTicket();

        Mockito.when(jwtUserDetailsServices.getUuidUser()).thenReturn(UUID.fromString("31347b9a-542e-4f48-a7d2-3c30c330ccc0"));
        Mockito.when(jwtUserDetailsServices.getEmailUser()).thenReturn("basic.simple@email.com");

        Mockito.when(usersDao.getUser(Mockito.any())).thenReturn(null);
        Mockito.doNothing().when(usersDao).insertNewUser(Mockito.any());
        Mockito.doNothing().when(ticketsDao).insertNewTicket(Mockito.any());
        Mockito.when(ticketsDao.getTicket(Mockito.any())).thenReturn(expected);

        TicketsResponse response = ticketsServices.create(TestUtils.createTicketsSimple(), new ArrayList<>());

        Assertions.assertEquals(expected, response.getData().get(0));
        Mockito.verify(jwtUserDetailsServices, Mockito.times(1)).getUuidUser();
        Mockito.verify(jwtUserDetailsServices, Mockito.times(1)).getEmailUser();
        Mockito.verify(usersDao, Mockito.times(1)).getUser(Mockito.any());
        Mockito.verify(ticketsDao, Mockito.times(1)).insertNewTicket(Mockito.any());
        Mockito.verify(ticketsDao, Mockito.times(1)).getTicket(Mockito.any());
    }

    @Test
    void createTicketsWithError() throws DaoException {
        Mockito.when(jwtUserDetailsServices.getUuidUser()).thenReturn(UUID.fromString("31347b9a-542e-4f48-a7d2-3c30c330ccc0"));
        Mockito.when(jwtUserDetailsServices.getEmailUser()).thenReturn("basic.simple@email.com");

        Mockito.when(usersDao.getUser(Mockito.any())).thenReturn(null);
        Mockito.doNothing().when(usersDao).insertNewUser(Mockito.any());
        Mockito.doThrow(new DaoException("An error was occur", HttpStatus.INTERNAL_SERVER_ERROR))
                .when(ticketsDao).insertNewTicket(Mockito.any());

        ApiError error = Assertions.assertThrows(ApiError.class,
                () -> ticketsServices.create(TestUtils.createTicketsSimple(), new ArrayList<>()));

        Assertions.assertEquals(TestUtils.createApiErrorForCreateTickets().getMessage(), error.getMessage());
        Mockito.verify(jwtUserDetailsServices, Mockito.times(1)).getUuidUser();
        Mockito.verify(jwtUserDetailsServices, Mockito.times(1)).getEmailUser();
        Mockito.verify(usersDao, Mockito.times(1)).getUser(Mockito.any());
        Mockito.verify(ticketsDao, Mockito.times(1)).insertNewTicket(Mockito.any());
    }

    @Test
    void getMyTickets() throws DaoException, ApiError {
        List<Ticket> expected = TestUtils.createTickets();

        Mockito.when(jwtUserDetailsServices.getUuidUser()).thenReturn(UUID.fromString("31347b9a-542e-4f48-a7d2-3c30c330ccc0"));
        Mockito.when(ticketsDao.getMyTickets(Mockito.any())).thenReturn(expected);

        TicketsResponse response = ticketsServices.myTickets();

        Assertions.assertEquals(expected, response.getData());
        Mockito.verify(jwtUserDetailsServices, Mockito.times(1)).getUuidUser();
        Mockito.verify(ticketsDao, Mockito.times(1)).getMyTickets(Mockito.any());
    }

    @Test
    void getMyTicketsWithError() throws DaoException {
        Mockito.when(jwtUserDetailsServices.getUuidUser()).thenReturn(UUID.fromString("31347b9a-542e-4f48-a7d2-3c30c330ccc0"));
        Mockito.when(ticketsDao.getMyTickets(Mockito.any()))
                .thenThrow(new DaoException("An error was occur", HttpStatus.INTERNAL_SERVER_ERROR));

        ApiError error = Assertions.assertThrows(ApiError.class, () -> ticketsServices.myTickets());

        Assertions.assertEquals(TestUtils.createApiErrorForGetMyTickets().getMessage(), error.getMessage());
        Mockito.verify(jwtUserDetailsServices, Mockito.times(1)).getUuidUser();
        Mockito.verify(ticketsDao, Mockito.times(1)).getMyTickets(Mockito.any());
    }

    @Test
    void deleteTicket() throws DaoException, ApiError {
        Ticket expected = TestUtils.createTicket();
        UUID uuid = UUID.fromString("65c94546-8565-47fd-9e91-05d68ea00bb7");

        Mockito.when(ticketsDao.getTicket(Mockito.any())).thenReturn(expected);
        Mockito.doNothing().when(ticketsDao).deleteTicket(Mockito.any());

        ticketsServices.delete(uuid);

        Mockito.verify(ticketsDao, Mockito.times(1)).deleteTicket(Mockito.any());
        Mockito.verify(ticketsDao, Mockito.times(1)).getTicket(Mockito.any());
    }

    @Test
    void deleteTicketNotFound() throws DaoException {
        UUID uuid = UUID.fromString("65c94546-8565-47fd-9e91-05d68ea00bb7");

        Mockito.when(ticketsDao.getTicket(Mockito.any()))
                .thenThrow(new DaoException("The ticket to delete is not found", HttpStatus.NOT_FOUND));

        ApiError error = Assertions.assertThrows(ApiError.class, () -> ticketsServices.delete(uuid));

        Assertions.assertEquals(TestUtils.createApiErrorForDeleteTicketNotFound().getMessage(), error.getMessage());
        Mockito.verify(ticketsDao, Mockito.times(1)).getTicket(Mockito.any());
    }

    @Test
    void deleteTicketWhenSearchError() throws DaoException {
        UUID uuid = UUID.fromString("65c94546-8565-47fd-9e91-05d68ea00bb7");

        Mockito.when(ticketsDao.getTicket(Mockito.any()))
                .thenThrow(new DaoException("An error was occur", HttpStatus.INTERNAL_SERVER_ERROR));

        ApiError error = Assertions.assertThrows(ApiError.class, () -> ticketsServices.delete(uuid));

        Assertions.assertEquals(TestUtils.createApiErrorForDeleteTicket().getMessage(), error.getMessage());
        Mockito.verify(ticketsDao, Mockito.times(1)).getTicket(Mockito.any());
    }

    @Test
    void deleteTicketWhenUpdateError() throws DaoException {
        Ticket expected = TestUtils.createTicket();
        UUID uuid = UUID.fromString("65c94546-8565-47fd-9e91-05d68ea00bb7");

        Mockito.when(ticketsDao.getTicket(Mockito.any())).thenReturn(expected);
        Mockito.doThrow(new DaoException("An error was occur", HttpStatus.INTERNAL_SERVER_ERROR))
                .when(ticketsDao).deleteTicket(Mockito.any());


        ApiError error = Assertions.assertThrows(ApiError.class, () -> ticketsServices.delete(uuid));

        Assertions.assertEquals(TestUtils.createApiErrorForDeleteTicket().getMessage(), error.getMessage());
        Mockito.verify(ticketsDao, Mockito.times(1)).getTicket(Mockito.any());
        Mockito.verify(ticketsDao, Mockito.times(1)).deleteTicket(Mockito.any());
    }
}