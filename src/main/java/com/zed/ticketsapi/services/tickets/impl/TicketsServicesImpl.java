package com.zed.ticketsapi.services.tickets.impl;

import com.zed.ticketsapi.controller.rest.models.User;
import com.zed.ticketsapi.controller.rest.models.errors.ApiError;
import com.zed.ticketsapi.controller.rest.models.tickets.Ticket;
import com.zed.ticketsapi.controller.rest.models.tickets.TicketsPayment;
import com.zed.ticketsapi.controller.rest.models.tickets.TicketSimple;
import com.zed.ticketsapi.controller.rest.models.tickets.TicketsResponse;
import com.zed.ticketsapi.dao.db.exceptions.DaoException;
import com.zed.ticketsapi.dao.db.tickets.TicketsDao;
import com.zed.ticketsapi.dao.db.users.UsersDao;
import com.zed.ticketsapi.services.jwt.JwtUserDetailsServices;
import com.zed.ticketsapi.services.tickets.TicketsServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketsServicesImpl implements TicketsServices {

    private final UsersDao usersDao;

    private final TicketsDao ticketsDao;

    private final JwtUserDetailsServices jwtUserDetailsServices;

    @Override
    public TicketsResponse payment(TicketsPayment ticketPayment) throws ApiError {
        List<UUID> uuids = new ArrayList<>();
        TicketsResponse results;
        int resultPayement = 0;

        results  = create(ticketPayment.getTickets(), uuids);
        try {
            resultPayement = mockPayment(ticketPayment);

        } catch (Exception e) {
            for (UUID uuid : uuids) {
                delete(uuid);
            }
        }

        if ((!Objects.equals(ticketPayment.getStatus(), "SUCCEEDED")) || (resultPayement != 1)) {
            for (UUID uuid : uuids) {
                delete(uuid);
            }
            log.error("Payment is invalid");
            throw ApiError.builder().message("The payement is invalid").code(HttpStatus.PAYMENT_REQUIRED).build();
        }

        return results;
    }

    @Override
    public TicketsResponse create(List<TicketSimple> ticketsToCreate, List<UUID> uuids) throws ApiError {
        List<Ticket> results = new ArrayList<>();

        User user;

        UUID userUuid = jwtUserDetailsServices.getUuidUser();
        String userEmail = jwtUserDetailsServices.getEmailUser();

        try {
            user = usersDao.getUser(userUuid);

            if (user == null) {
                usersDao.insertNewUser(User.builder().uuid(userUuid).email(userEmail).build());
            }

            for (TicketSimple ticketSimple : ticketsToCreate) {
                UUID uuidRandom = UUID.randomUUID();

                ticketsDao.insertNewTicket(
                        Ticket.builder()
                        .uuid(uuidRandom)
                        .firstname(ticketSimple.getFirstname())
                        .lastname(ticketSimple.getLastname())
                        .date(LocalDate.now())
                        .userId(userUuid)
                        .build());

                uuids.add(uuidRandom);
            }

            for (UUID id : uuids) {
                results.add(ticketsDao.getTicket(id));
            }

        } catch (DaoException e) {
            log.error("Error occurred when create new ticket. Error : {}, code : {}", e.getMessage(), e.getCode());
            throw ApiError.builder().message("An error occurred when create new ticket.").code(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return TicketsResponse.builder().data(results).build();
    }

    @Override
    public TicketsResponse myTickets() throws ApiError {
        List<Ticket> results;

        UUID userUuid = jwtUserDetailsServices.getUuidUser();

        try {
            results = ticketsDao.getMyTickets(userUuid);
        } catch (DaoException e) {
            log.error("Error occurred when get your tickets. Error : {}, code : {}", e.getMessage(), e.getCode());
            throw ApiError.builder().message("An error occurred when get your tickets.").code(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return TicketsResponse.builder().data(results).build();
    }

    @Override
    public void delete(UUID uuid) throws ApiError {
        try {
            ticketsDao.getTicket(uuid);
        } catch (DaoException e) {
            if (e.getCode() == HttpStatus.NOT_FOUND) {
                log.error("Delete cancel, the ticket is not found");
                throw ApiError.builder().message("The ticket to delete is not found").code(e.getCode()).build();
            }
            log.error("Error occurred when delete a ticket. Error : {}, code : {}", e.getMessage(), e.getCode());
            throw ApiError.builder().message("An error occurred when delete a ticket.").code(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        try {
            ticketsDao.deleteTicket(uuid);
        } catch (DaoException e) {
            log.error("Error occurred when delete a ticket. Error : {}, code : {}", e.getMessage(), e.getCode());
            throw ApiError.builder().message("An error occurred when delete a ticket.").code(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private int mockPayment(TicketsPayment ticketPayment) {


        log.info("Call to Stripe with object card");

        log.info("Initialisation of payment");
        ticketPayment.setStatus("INITIALIZE");

        //stripeDao.payment(ticketPayment.getCard());

        log.info("Payment in progress");

        //if (Payment is OK ) {
            log.info("Payment successful");
            ticketPayment.setStatus("SUCCEEDED");
            return 1;
        //} else {
            //return 0
        //}
    }
}