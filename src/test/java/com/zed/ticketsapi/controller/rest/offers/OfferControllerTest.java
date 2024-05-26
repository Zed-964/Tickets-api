package com.zed.ticketsapi.controller.rest.offers;

import com.zed.ticketsapi.controller.rest.models.JwtKeycloak;
import com.zed.ticketsapi.controller.rest.models.errors.ApiError;
import com.zed.ticketsapi.controller.rest.models.offers.OfferResponse;
import com.zed.ticketsapi.controller.rest.models.offers.OffersResponse;
import com.zed.ticketsapi.services.offers.OffersServices;
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
@WebMvcTest(controllers = OffersController.class)
class OfferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OffersServices offersServices;

    private JwtKeycloak jwt;

    @BeforeEach
    public void getJwt() {
        this.jwt = TestUtils.getTokenKeycloak();
    }

    @Test
    void getAllOffers() throws Exception, ApiError {
        OffersResponse expected = TestUtils.createOffersResponse();

        Mockito.when(offersServices.allOffers()).thenReturn(expected);

        MvcResult obtained = mockMvc.perform(MockMvcRequestBuilders.get("/offers")
                        .header("Authorization", jwt.getToken_type() + " " + jwt.getAccess_token())
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andReturn();

        JSONAssert.assertEquals(TestUtils.createOffersResponseJson(), obtained.getResponse().getContentAsString(), JSONCompareMode.LENIENT);
    }

    @Test
    void getAllOffersWithError500() throws Exception, ApiError {

        Mockito.when(offersServices.allOffers()).thenThrow(ApiError.builder()
                .message("An error was occur").code(HttpStatus.INTERNAL_SERVER_ERROR).build());

        mockMvc.perform(MockMvcRequestBuilders.get("/offers")
                        .header("Authorization", jwt.getToken_type() + " " + jwt.getAccess_token())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void postOffer() throws ApiError, Exception {
         OfferResponse expected = TestUtils.createOfferResponse();

        Mockito.when(offersServices.create(Mockito.any())).thenReturn(expected);

        MvcResult obtained = mockMvc.perform(MockMvcRequestBuilders.post("/offers")
                        .header("Authorization", jwt.getToken_type() + " " + jwt.getAccess_token())
                        .content(TestUtils.createOfferSimpleJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        JSONAssert.assertEquals(TestUtils.createOfferResponseJson(), obtained.getResponse().getContentAsString(), JSONCompareMode.LENIENT);

    }

    @Test
    void postOfferWithError4xx() throws ApiError, Exception {
        OfferResponse expected = TestUtils.createOfferResponse();

        Mockito.when(offersServices.create(Mockito.any())).thenReturn(expected);

        mockMvc.perform(MockMvcRequestBuilders.post("/offers")
                        .content(TestUtils.createOfferSimpleJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    void postOfferWithError500() throws ApiError, Exception {
        Mockito.when(offersServices.create(Mockito.any())).thenThrow(ApiError.builder()
                .message("An error was occur").code(HttpStatus.INTERNAL_SERVER_ERROR).build());

        mockMvc.perform(MockMvcRequestBuilders.post("/offers")
                        .header("Authorization", jwt.getToken_type() + " " + jwt.getAccess_token())
                        .content(TestUtils.createOfferSimpleJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    void putOffer() throws ApiError, Exception {
        OfferResponse expected = TestUtils.createOfferResponse();

        Mockito.when(offersServices.update(Mockito.any(), Mockito.any())).thenReturn(expected);

        MvcResult obtained = mockMvc.perform(MockMvcRequestBuilders.put("/offers/65c94546-8565-47fd-9e91-05d68ea00bb7")
                        .header("Authorization", jwt.getToken_type() + " " + jwt.getAccess_token())
                        .content(TestUtils.createOfferJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        JSONAssert.assertEquals(TestUtils.createOfferResponseJson(), obtained.getResponse().getContentAsString(), JSONCompareMode.LENIENT);
    }

    @Test
    void putOfferWithErrorUuidLengthIsInvalid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/offers/65c94546-8565-47fd-9e91-05d68ea00bb70")
                        .header("Authorization", jwt.getToken_type() + " " + jwt.getAccess_token())
                        .content(TestUtils.createOfferJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void putOfferWithErrorChangeUuidIsInvalid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/offers/65c94546-8565-47fd-9e91-05d68ea00bb0")
                        .header("Authorization", jwt.getToken_type() + " " + jwt.getAccess_token())
                        .content(TestUtils.createOfferWithUuidChangeJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void putOfferWithErrorChangeUuidFormatIsInvalid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/offers/65c94546-8-65-47-d-9e-1-05d-8ea0-bb7")
                        .header("Authorization", jwt.getToken_type() + " " + jwt.getAccess_token())
                        .content(TestUtils.createOfferWithUuidChangeJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void putOfferWithErrorIsForbidden() throws ApiError, Exception {
        OfferResponse expected = TestUtils.createOfferResponse();

        Mockito.when(offersServices.update(Mockito.any(), Mockito.any())).thenReturn(expected);

        mockMvc.perform(MockMvcRequestBuilders.put("/offers/65c94546-8565-47fd-9e91-05d68ea00bb7")
                        .content(TestUtils.createOfferJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void putOfferWithError500() throws ApiError, Exception {
        Mockito.when(offersServices.update(Mockito.any(), Mockito.any())).thenThrow(ApiError.builder()
                .message("An error was occur").code(HttpStatus.INTERNAL_SERVER_ERROR).build());

        mockMvc.perform(MockMvcRequestBuilders.put("/offers/65c94546-8565-47fd-9e91-05d68ea00bb7")
                        .header("Authorization", jwt.getToken_type() + " " + jwt.getAccess_token())
                        .content(TestUtils.createOfferJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    void deleteOffer() throws ApiError, Exception {
        Mockito.doNothing().when(offersServices).delete(Mockito.any());

        mockMvc.perform(MockMvcRequestBuilders.delete("/offers/65c94546-8565-47fd-9e91-05d68ea00bb7")
                        .header("Authorization", jwt.getToken_type() + " " + jwt.getAccess_token())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void deleteOfferWithErrorUuidLengthIsInvalid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/offers/65c94546-8565-47fd-9e91-05d68ea00bb70")
                        .header("Authorization", jwt.getToken_type() + " " + jwt.getAccess_token())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void deleteOfferWithErrorUuidFormatIsInvalid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/offers/65c94546-8-65-4-fd-9e-1-05d6-ea0-bb0")
                        .header("Authorization", jwt.getToken_type() + " " + jwt.getAccess_token())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void deleteOfferWithErrorIsForbidden() throws ApiError, Exception {
        Mockito.doNothing().when(offersServices).delete(Mockito.any());

        mockMvc.perform(MockMvcRequestBuilders.delete("/offers/65c94546-8565-47fd-9e91-05d68ea00bb7")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void deleteOfferWithError500() throws ApiError, Exception {
        Mockito.doThrow(ApiError.builder().message("An error was occur").code(HttpStatus.INTERNAL_SERVER_ERROR).build())
                        .when(offersServices).delete(Mockito.any());

        mockMvc.perform(MockMvcRequestBuilders.delete("/offers/65c94546-8565-47fd-9e91-05d68ea00bb7")
                        .header("Authorization", jwt.getToken_type() + " " + jwt.getAccess_token())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }
}