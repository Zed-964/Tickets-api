package com.zed.ticketsapi.utils;

import com.zed.ticketsapi.controller.rest.models.JwtKeycloak;
import com.zed.ticketsapi.controller.rest.models.User;
import com.zed.ticketsapi.controller.rest.models.errors.ApiError;
import com.zed.ticketsapi.controller.rest.models.offers.Offer;
import com.zed.ticketsapi.controller.rest.models.offers.OfferResponse;
import com.zed.ticketsapi.controller.rest.models.offers.OfferSimple;
import com.zed.ticketsapi.controller.rest.models.offers.OffersResponse;
import com.zed.ticketsapi.controller.rest.models.tickets.Ticket;
import com.zed.ticketsapi.controller.rest.models.tickets.TicketSimple;
import com.zed.ticketsapi.controller.rest.models.tickets.TicketsResponse;
import com.zed.ticketsapi.dao.db.exceptions.DaoException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestUtils {

    private TestUtils() {

    }

    public static TicketsResponse createTicketsResponse() {
        return TicketsResponse.builder()
                .data(List.of(Ticket.builder()
                        .uuid(UUID.fromString("65c94546-8565-47fd-9e91-05d68ea00bb7"))
                        .firstname("test1")
                        .lastname("testing1")
                        .date(LocalDate.of(2024, 1, 1))
                        .userId(UUID.fromString("31347b9a-542e-4f48-a7d2-3c30c330ccc0"))
                        .build()))
                .build();
    }

    public static OffersResponse createOffersResponse() {
        return OffersResponse.builder()
                .data(List.of(Offer.builder()
                        .uuid(UUID.fromString("65c94546-8565-47fd-9e91-05d68ea00bb7"))
                        .name("offer1")
                        .description("description1")
                        .numberTickets(1)
                        .price(9.99F)
                        .build()))
                .build();
    }

    public static OfferResponse createOfferResponse() {
        return OfferResponse.builder()
                .data(Offer.builder()
                        .uuid(UUID.fromString("65c94546-8565-47fd-9e91-05d68ea00bb7"))
                        .name("offer1")
                        .description("description1")
                        .numberTickets(1)
                        .price(9.99F)
                        .build())
                .build();
    }

    public static Offer createOffer() {
        return Offer.builder()
                .uuid(UUID.fromString("65c94546-8565-47fd-9e91-05d68ea00bb7"))
                .name("offer1")
                .description("description1")
                .numberTickets(1)
                .price(9.99F)
                .build();
    }

    public static Ticket createTicket() {
        return Ticket.builder()
                .uuid(UUID.fromString("65c94546-8565-47fd-9e91-05d68ea00bb7"))
                .firstname("test1")
                .lastname("testing1")
                .date(LocalDate.of(2024, 1, 1))
                .userId(UUID.fromString("31347b9a-542e-4f48-a7d2-3c30c330ccc0"))
                .build();
    }

    public static List<Offer> createOffers() {
        return List.of(Offer.builder()
                .uuid(UUID.fromString("65c94546-8565-47fd-9e91-05d68ea00bb7"))
                .name("offer1")
                .description("description1")
                .numberTickets(1)
                .price(9.99F)
                .build());
    }

    public static List<Ticket> createTickets() {
        return new ArrayList<>(List.of(Ticket.builder()
                .uuid(UUID.fromString("65c94546-8565-47fd-9e91-05d68ea00bb7"))
                .firstname("test1")
                .lastname("testing1")
                .date(LocalDate.of(2024, 1, 1))
                .userId(UUID.fromString("31347b9a-542e-4f48-a7d2-3c30c330ccc0"))
                .build()));
    }

    public static OfferSimple createOfferSimple() {
        return OfferSimple.builder()
                .name("offer1")
                .description("description1")
                .numberTickets(1)
                .price(9.99F)
                .build();
    }

    public static User createUser() {
        return User.builder()
                .uuid(UUID.fromString("31347b9a-542e-4f48-a7d2-3c30c330ccc0"))
                .email("basic.simple@email.com")
                .build();
    }

    public static List<TicketSimple> createTicketsSimple() {
        return List.of(TicketSimple.builder()
                .firstname("tes1")
                .lastname("testing1")
                .build());
    }

    public static String createOfferSimpleJson() {
        return """
                {
                  "name": "offer1",
                  "description": "description1",
                  "numberTickets": 1,
                  "price": 9.99
                }
                """;
    }

    public static String createOfferJson() {
        return """
                {
                  "uuid": "65c94546-8565-47fd-9e91-05d68ea00bb7",
                  "name": "offer1",
                  "description": "description1",
                  "numberTickets": 1,
                  "price": 9.99
                }
                """;
    }

    public static String createOfferWithUuidChangeJson() {
        return """
                {
                  "uuid": "65c94546-8565-47fd-9e91-05d68ea00bb7",
                  "name": "offer1",
                  "description": "description1",
                  "numberTickets": 1,
                  "price": 9.99
                }
                """;
    }

    public static String createOfferResponseJson() {
        return """
                {
                  "data": {
                    "uuid": "65c94546-8565-47fd-9e91-05d68ea00bb7",
                    "name": "offer1",
                    "description": "description1",
                    "numberTickets": 1,
                    "price": 9.99
                  }
                }
                """;
    }

    public static String createOffersResponseJson() {
        return """
                {
                  "data": [
                  {
                    "uuid": "65c94546-8565-47fd-9e91-05d68ea00bb7",
                    "name": "offer1",
                    "description": "description1",
                    "numberTickets": 1,
                    "price": 9.99
                    }
                  ]
                }
                """;
    }

    public static String createTicketsResponseJson() {
        return """
                {
                  "data": [
                  {
                    "uuid": "65c94546-8565-47fd-9e91-05d68ea00bb7",
                    "firstname": "test1",
                    "lastname": "testing1",
                    "date": "2024-01-01",
                    "userId": "31347b9a-542e-4f48-a7d2-3c30c330ccc0"
                    }
                  ]
                }
                """;
    }

    public static String createTicketPaymentJson() {
        return """
                {
                  "card": {
                    "number": "4973559924144258",
                    "holderName": "JOHN DOE",
                    "expirationDate": "01/23",
                    "securityCode": "696"
                  },
                  "mount": "99.99",
                  "status": "INITIALIZE",
                  "tickets": [
                    {
                      "firstname": "test1",
                      "lastname": "testing1"
                    }
                  ]
                }
                """;
    }

    public static ApiError createApiErrorForCreateOffer() {
        return ApiError.builder()
                .message("An error occurred when create new offer.")
                .code(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
    }

    public static ApiError createApiErrorForUpdateOffer() {
        return ApiError.builder()
                .message("An error occurred when update a offer.")
                .code(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
    }

    public static ApiError createApiErrorForUpdateOfferNotFound() {
        return ApiError.builder()
                .message("The offer to update is not found")
                .code(HttpStatus.NOT_FOUND)
                .build();
    }

    public static ApiError createApiErrorForGetALlOffersNotFound() {
        return ApiError.builder()
                .message("None of the offer has found")
                .code(HttpStatus.NOT_FOUND)
                .build();
    }

    public static ApiError createApiErrorForGetALlOffersIsError() {
        return ApiError.builder()
                .message("An error occurred when get all offer.")
                .code(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
    }

    public static ApiError createApiErrorForDeleteOfferNotFound() {
        return ApiError.builder()
                .message("The offer to delete is not found")
                .code(HttpStatus.NOT_FOUND)
                .build();
    }

    public static ApiError createApiErrorForDeleteTicketNotFound() {
        return ApiError.builder()
                .message("The ticket to delete is not found")
                .code(HttpStatus.NOT_FOUND)
                .build();
    }

    public static ApiError createApiErrorForDeleteOffer() {
        return ApiError.builder()
                .message("An error occurred when delete a offer.")
                .code(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
    }

    public static ApiError createApiErrorForDeleteTicket() {
        return ApiError.builder()
                .message("An error occurred when delete a ticket.")
                .code(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
    }

    public static ApiError createApiErrorForGetMyTickets() {
        return ApiError.builder()
                .message("An error occurred when get your tickets.")
                .code(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
    }

    public static ApiError createApiErrorForCreateTickets() {
        return ApiError.builder()
                .message("An error occurred when create new ticket.")
                .code(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
    }

    public static DaoException createDaoExceptionExecutingQuery() {
        return new DaoException("An error occurred when executing query", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static DaoException createDaoException() {
        return new DaoException("An error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static DaoException createDaoExceptionTicketNotFound() {
        return new DaoException("The ticket has not found", HttpStatus.NOT_FOUND);
    }

    public static DaoException createDaoExceptionTicketToDeleteNotFound() {
        return new DaoException("Ticket to delete is not found", HttpStatus.NOT_FOUND);
    }

    public static DaoException createDaoExceptionOfferToDeleteNotFound() {
        return new DaoException("Offer to delete is not found", HttpStatus.NOT_FOUND);
    }

    public static DaoException createDaoExceptionOfferToUpdateNotFound() {
        return new DaoException("Offer to update is not found", HttpStatus.NOT_FOUND);
    }

    public static JwtKeycloak getTokenKeycloak() {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8081/realms/jo-tickets-distribution/protocol/openid-connect/token";

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "password");
        requestBody.add("client_id", "tickets-front");
        requestBody.add("username", "jo-tickets-admin");
        requestBody.add("password", "T85YDHbs$b&dhCzRPk?8#c#oo3rmhM?CrN833anS");

        final var requestEntity = RequestEntity.post(builder.toUriString())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .body(requestBody);

        final ParameterizedTypeReference<JwtKeycloak> responseType = new ParameterizedTypeReference<>() {
        };

        return restTemplate.exchange(requestEntity, responseType).getBody();
    }
}
