package com.zed.ticketsapi.services.offers;

import com.zed.ticketsapi.controller.rest.models.errors.ApiError;
import com.zed.ticketsapi.controller.rest.models.offers.Offer;
import com.zed.ticketsapi.controller.rest.models.offers.OfferResponse;
import com.zed.ticketsapi.controller.rest.models.offers.OffersResponse;
import com.zed.ticketsapi.dao.db.exceptions.DaoException;
import com.zed.ticketsapi.dao.db.offers.OffersDao;
import com.zed.ticketsapi.services.offers.impl.OffersServicesImpl;
import com.zed.ticketsapi.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.aot.DisabledInAotMode;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@DisabledInAotMode
class OffersServicesTest {

    @Mock
    private OffersDao offersDao;

    @InjectMocks
    OffersServicesImpl offersServices;

    @Test
    void createOffer() throws ApiError, DaoException {
        Offer expected = TestUtils.createOffer();

        Mockito.doNothing().when(offersDao).insertNewOffer(Mockito.any());
        Mockito.when(offersDao.getOffer(Mockito.any())).thenReturn(expected);

        OfferResponse response = offersServices.create(TestUtils.createOfferSimple());

        Assertions.assertEquals(expected, response.getData());
        Mockito.verify(offersDao, Mockito.times(1)).insertNewOffer(Mockito.any());
        Mockito.verify(offersDao, Mockito.times(1)).getOffer(Mockito.any());
    }

    @Test
    void createOfferWithError() throws DaoException {
        Mockito.doThrow(new DaoException("An error has occur", HttpStatus.INTERNAL_SERVER_ERROR))
                .when(offersDao).insertNewOffer(Mockito.any());

        ApiError error = Assertions.assertThrows(ApiError.class, () -> offersServices.create(TestUtils.createOfferSimple()));

        Assertions.assertEquals(TestUtils.createApiErrorForCreateOffer().getMessage(), error.getMessage());
        Mockito.verify(offersDao, Mockito.times(1)).insertNewOffer(Mockito.any());
    }

    @Test
    void updateOffer() throws DaoException, ApiError {
        Offer expected = TestUtils.createOffer();
        UUID uuid = UUID.fromString("65c94546-8565-47fd-9e91-05d68ea00bb7");

        Mockito.when(offersDao.getOffer(Mockito.any())).thenReturn(expected);
        Mockito.doNothing().when(offersDao).updateOffer(Mockito.any());

        OfferResponse response = offersServices.update(uuid, TestUtils.createOffer());

        Assertions.assertEquals(expected, response.getData());
        Mockito.verify(offersDao, Mockito.times(1)).updateOffer(Mockito.any());
        Mockito.verify(offersDao, Mockito.times(2)).getOffer(Mockito.any());
    }

    @Test
    void updateOfferNotFound() throws DaoException {
        UUID uuid = UUID.fromString("65c94546-8565-47fd-9e91-05d68ea00bb7");

        Mockito.when(offersDao.getOffer(Mockito.any())).thenThrow(new DaoException("An error was occur", HttpStatus.NOT_FOUND));

        ApiError error = Assertions.assertThrows(ApiError.class, () -> offersServices.update(uuid, TestUtils.createOffer()));

        Assertions.assertEquals(TestUtils.createApiErrorForUpdateOfferNotFound().getMessage(), error.getMessage());
        Mockito.verify(offersDao, Mockito.times(1)).getOffer(Mockito.any());
    }

    @Test
    void updateOfferWhenSearchError() throws DaoException {
        UUID uuid = UUID.fromString("65c94546-8565-47fd-9e91-05d68ea00bb7");

        Mockito.when(offersDao.getOffer(Mockito.any()))
                .thenThrow(new DaoException("An error was occur", HttpStatus.INTERNAL_SERVER_ERROR));

        ApiError error = Assertions.assertThrows(ApiError.class, () -> offersServices.update(uuid, TestUtils.createOffer()));

        Assertions.assertEquals(TestUtils.createApiErrorForUpdateOffer().getMessage(), error.getMessage());
        Mockito.verify(offersDao, Mockito.times(1)).getOffer(Mockito.any());
    }

    @Test
    void updateOfferWhenUpdateError() throws DaoException {
        UUID uuid = UUID.fromString("65c94546-8565-47fd-9e91-05d68ea00bb7");

        Mockito.doThrow(new DaoException("An error was occur", HttpStatus.INTERNAL_SERVER_ERROR))
                .when(offersDao).updateOffer(Mockito.any());

        ApiError error = Assertions.assertThrows(ApiError.class, () -> offersServices.update(uuid, TestUtils.createOffer()));

        Assertions.assertEquals(TestUtils.createApiErrorForUpdateOffer().getMessage(), error.getMessage());
        Mockito.verify(offersDao, Mockito.times(1)).getOffer(Mockito.any());
        Mockito.verify(offersDao, Mockito.times(1)).updateOffer(Mockito.any());
    }

    @Test
    void getAllOffer() throws ApiError, DaoException {
        List<Offer> expected = TestUtils.createOffers();

        Mockito.when(offersDao.getAllOffer()).thenReturn(expected);

        OffersResponse response = offersServices.allOffers();

        Assertions.assertEquals(expected, response.getData());
        Mockito.verify(offersDao, Mockito.times(1)).getAllOffer();
    }

    @Test
    void getAllOfferWithErrorNotFound() throws DaoException {
        Mockito.when(offersDao.getAllOffer())
                .thenThrow(new DaoException("None of the offers has been found", HttpStatus.NOT_FOUND));

        ApiError error = Assertions.assertThrows(ApiError.class, () -> offersServices.allOffers());

        Assertions.assertEquals(TestUtils.createApiErrorForGetALlOffersNotFound().getMessage(), error.getMessage());
        Mockito.verify(offersDao, Mockito.times(1)).getAllOffer();
    }

    @Test
    void getAllOfferWithError() throws DaoException {
        Mockito.when(offersDao.getAllOffer())
                .thenThrow(new DaoException("An error was occur", HttpStatus.INTERNAL_SERVER_ERROR));

        ApiError error = Assertions.assertThrows(ApiError.class, () -> offersServices.allOffers());

        Assertions.assertEquals(TestUtils.createApiErrorForGetALlOffersIsError().getMessage(), error.getMessage());
        Mockito.verify(offersDao, Mockito.times(1)).getAllOffer();
    }

    @Test
    void deleteOffer() throws DaoException, ApiError {
        Offer expected = TestUtils.createOffer();
        UUID uuid = UUID.fromString("65c94546-8565-47fd-9e91-05d68ea00bb7");

        Mockito.when(offersDao.getOffer(Mockito.any())).thenReturn(expected);
        Mockito.doNothing().when(offersDao).deleteOffer(Mockito.any());

        offersServices.delete(uuid);

        Mockito.verify(offersDao, Mockito.times(1)).deleteOffer(Mockito.any());
        Mockito.verify(offersDao, Mockito.times(1)).getOffer(Mockito.any());
    }

    @Test
    void deleteOfferNotFound() throws DaoException {
        UUID uuid = UUID.fromString("65c94546-8565-47fd-9e91-05d68ea00bb7");

        Mockito.when(offersDao.getOffer(Mockito.any()))
                .thenThrow(new DaoException("The offer to delete is not found", HttpStatus.NOT_FOUND));

        ApiError error = Assertions.assertThrows(ApiError.class, () -> offersServices.delete(uuid));

        Assertions.assertEquals(TestUtils.createApiErrorForDeleteOfferNotFound().getMessage(), error.getMessage());
        Mockito.verify(offersDao, Mockito.times(1)).getOffer(Mockito.any());
    }

    @Test
    void deleteOfferWhenSearchError() throws DaoException {
        UUID uuid = UUID.fromString("65c94546-8565-47fd-9e91-05d68ea00bb7");

        Mockito.when(offersDao.getOffer(Mockito.any()))
                .thenThrow(new DaoException("An error was occur", HttpStatus.INTERNAL_SERVER_ERROR));

        ApiError error = Assertions.assertThrows(ApiError.class, () -> offersServices.delete(uuid));

        Assertions.assertEquals(TestUtils.createApiErrorForDeleteOffer().getMessage(), error.getMessage());
        Mockito.verify(offersDao, Mockito.times(1)).getOffer(Mockito.any());
    }

    @Test
    void deleteOfferWhenUpdateError() throws DaoException {
        Offer expected = TestUtils.createOffer();
        UUID uuid = UUID.fromString("65c94546-8565-47fd-9e91-05d68ea00bb7");

        Mockito.when(offersDao.getOffer(Mockito.any())).thenReturn(expected);
        Mockito.doThrow(new DaoException("An error was occur", HttpStatus.INTERNAL_SERVER_ERROR)).when(offersDao).deleteOffer(Mockito.any());


        ApiError error = Assertions.assertThrows(ApiError.class, () -> offersServices.delete(uuid));

        Assertions.assertEquals(TestUtils.createApiErrorForDeleteOffer().getMessage(), error.getMessage());
        Mockito.verify(offersDao, Mockito.times(1)).getOffer(Mockito.any());
        Mockito.verify(offersDao, Mockito.times(1)).deleteOffer(Mockito.any());
    }
}
