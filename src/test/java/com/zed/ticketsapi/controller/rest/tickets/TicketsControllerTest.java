package com.zed.ticketsapi.controller.rest.tickets;

import com.zed.ticketsapi.controller.rest.models.JwtKeycloak;
import com.zed.ticketsapi.controller.rest.models.errors.ApiError;
import com.zed.ticketsapi.controller.rest.models.tickets.TicketsPayment;
import com.zed.ticketsapi.controller.rest.models.tickets.TicketsResponse;
import com.zed.ticketsapi.services.tickets.TicketsServices;
import com.zed.ticketsapi.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@DisabledInAotMode
@WebMvcTest(controllers = TicketsController.class)
class TicketsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketsServices ticketsServices;

    private JwtKeycloak jwt;

    @BeforeEach
    public void getJwt() {
        this.jwt = TestUtils.getTokenKeycloak();
    }

    @Test
    void getMyTickets() throws ApiError, Exception {
        TicketsResponse expected = TestUtils.createTicketsResponse();

        Mockito.when(ticketsServices.myTickets()).thenReturn(expected);

        MvcResult obtained = mockMvc.perform(MockMvcRequestBuilders.get("/tickets/me")
                        .header("Authorization", jwt.getToken_type() + " " + jwt.getAccess_token())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        JSONAssert.assertEquals(TestUtils.createTicketsResponseJson(), obtained.getResponse().getContentAsString(), JSONCompareMode.LENIENT);
    }

    @Test
    void getMyTicketsWithErrorIsUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/tickets/me")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void getMyTicketsWithError500() throws ApiError, Exception {
        Mockito.when(ticketsServices.myTickets()).thenThrow(ApiError.builder()
                .message("An error was occur").code(HttpStatus.INTERNAL_SERVER_ERROR).build());

        mockMvc.perform(MockMvcRequestBuilders.get("/tickets/me")
                        .header("Authorization", jwt.getToken_type() + " " + jwt.getAccess_token())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    void postMyTickets() throws ApiError, Exception {
        TicketsResponse expected = TestUtils.createTicketsResponse();

        Mockito.when(ticketsServices.payment(Mockito.any())).thenReturn(expected);

        MvcResult obtained = mockMvc.perform(MockMvcRequestBuilders.post("/tickets/payment")
                        .header("Authorization", jwt.getToken_type() + " " + jwt.getAccess_token())
                        .content(TestUtils.createTicketPaymentJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        JSONAssert.assertEquals(TestUtils.createTicketsResponseJson(), obtained.getResponse().getContentAsString(), JSONCompareMode.LENIENT);
    }

    @Test
    void postMyTicketsWithError400() throws Exception {
        String ticketsPayment = TestUtils.createWrongFormatTicketPaymentJson();

        mockMvc.perform(MockMvcRequestBuilders.post("/tickets/payment")
                        .header("Authorization", jwt.getToken_type() + " " + jwt.getAccess_token())
                        .content(ticketsPayment)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void postMyTicketsWithError500() throws ApiError, Exception {
        Mockito.when(ticketsServices.payment(Mockito.any())).thenThrow(ApiError.builder()
                .message("An error was occur").code(HttpStatus.INTERNAL_SERVER_ERROR).build());

        mockMvc.perform(MockMvcRequestBuilders.post("/tickets/payment")
                        .header("Authorization", jwt.getToken_type() + " " + jwt.getAccess_token())
                        .content(TestUtils.createTicketPaymentJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }
}
