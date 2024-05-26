package com.zed.ticketsapi.dao.db.offers;

import com.zed.ticketsapi.controller.rest.models.offers.Offer;
import com.zed.ticketsapi.dao.db.exceptions.DaoException;
import com.zed.ticketsapi.dao.db.offers.impl.OffersDaoImpl;
import com.zed.ticketsapi.mapper.OfferMapper;
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

import java.util.List;
import java.util.UUID;

@SpringBootTest
@DisabledInAotMode
class OffersDaoTest {

    @Mock
    JdbcTemplate jdbcTemplate;

    private OffersDaoImpl offersDao;

    @BeforeEach
    public void setUp() {
        offersDao = Mockito.spy(new OffersDaoImpl(jdbcTemplate));
    }

    @Test
    void getMyOffers() throws DaoException {
        List<Offer> expected = TestUtils.createOffers();

        String query = "SELECT * FROM t_offers";

        Mockito.when(this.jdbcTemplate.query(Mockito.anyString(), Mockito.any(OfferMapper.class))).thenReturn(expected);

        var response = offersDao.getAllOffer();

        Assertions.assertEquals(expected, response);
        Mockito.verify(jdbcTemplate, Mockito.times(1))
                .query(Mockito.eq(query), Mockito.any(OfferMapper.class));
    }

    @Test
    void getMyOffersWithError() {
        String query = "SELECT * FROM t_offers";

        Mockito.when(this.jdbcTemplate.query(Mockito.anyString(), Mockito.any(OfferMapper.class)))
                .thenThrow(new DataAccessException("error") {});

        DaoException error = Assertions.assertThrows(DaoException.class, () -> offersDao.getAllOffer());

        Assertions.assertEquals(TestUtils.createDaoExceptionExecutingQuery().getMessage(), error.getMessage());
        Mockito.verify(jdbcTemplate, Mockito.times(1))
                .query(Mockito.eq(query), Mockito.any(OfferMapper.class));
    }

    @Test
    void getOffer() throws DaoException {
        Offer expected = TestUtils.createOffer();
        String query = "SELECT * FROM t_offers WHERE idt_off = ?";

        Mockito.when(this.jdbcTemplate.queryForObject(Mockito.anyString(), Mockito.any(OfferMapper.class), Mockito.any()))
                .thenReturn(expected);

        var response = offersDao.getOffer(expected.getUuid());

        Assertions.assertEquals(expected, response);
        Mockito.verify(jdbcTemplate, Mockito.times(1))
                .queryForObject(Mockito.eq(query), Mockito.any(OfferMapper.class), Mockito.any());
    }

    @Test
    void getOfferNotFound() {
        String query = "SELECT * FROM t_offers WHERE idt_off = ?";

        Mockito.when(this.jdbcTemplate.queryForObject(Mockito.anyString(), Mockito.any(OfferMapper.class), Mockito.any()))
                .thenThrow(new EmptyResultDataAccessException(0));

        DaoException error = Assertions.assertThrows(DaoException.class, () -> offersDao.getOffer(TestUtils.createTicket().getUuid()));

        Assertions.assertEquals(TestUtils.createDaoExceptionExecutingQuery().getMessage(), error.getMessage());
        Mockito.verify(jdbcTemplate, Mockito.times(1))
                .queryForObject(Mockito.eq(query), Mockito.any(OfferMapper.class), Mockito.any());
    }

    @Test
    void getOfferWithError() {
        String query = "SELECT * FROM t_offers WHERE idt_off = ?";

        Mockito.when(this.jdbcTemplate.queryForObject(Mockito.anyString(), Mockito.any(OfferMapper.class), Mockito.any()))
                .thenThrow(new DataAccessException("error") {});

        DaoException error = Assertions.assertThrows(DaoException.class, () -> offersDao.getOffer(TestUtils.createTicket().getUuid()));

        Assertions.assertEquals(TestUtils.createDaoExceptionExecutingQuery().getMessage(), error.getMessage());
        Mockito.verify(jdbcTemplate, Mockito.times(1))
                .queryForObject(Mockito.eq(query), Mockito.any(OfferMapper.class), Mockito.any());
    }

    @Test
    void insertOffer() throws DaoException {
        String query = "INSERT INTO t_offers (idt_off, lib_off, lib_des, num_tick, num_price) VALUES (?,?,?,?,?)";

        Mockito.when(this.jdbcTemplate.update(Mockito.anyString(), Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.any(), Mockito.any()))
                .thenReturn(1);

        offersDao.insertNewOffer(TestUtils.createOffer());

        Mockito.verify(jdbcTemplate, Mockito.times(1))
                .update(Mockito.eq(query), Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.any(), Mockito.any());
    }

    @Test
    void insertOfferWithError() {
        String query = "INSERT INTO t_offers (idt_off, lib_off, lib_des, num_tick, num_price) VALUES (?,?,?,?,?)";

        Mockito.when(this.jdbcTemplate.update(Mockito.anyString(), Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.any(), Mockito.any()))
                .thenThrow(new DataAccessException("error") {});

        DaoException error = Assertions.assertThrows(DaoException.class, () -> offersDao.insertNewOffer(TestUtils.createOffer()));

        Assertions.assertEquals(TestUtils.createDaoExceptionExecutingQuery().getMessage(), error.getMessage());
        Mockito.verify(jdbcTemplate, Mockito.times(1))
                .update(Mockito.eq(query), Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.any(), Mockito.any());
    }

    @Test
    void insertOfferWithResult() {
        String query = "INSERT INTO t_offers (idt_off, lib_off, lib_des, num_tick, num_price) VALUES (?,?,?,?,?)";

        Mockito.when(this.jdbcTemplate.update(Mockito.anyString(), Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.any(), Mockito.any())).thenReturn(0);

        DaoException error = Assertions.assertThrows(DaoException.class, () -> offersDao.insertNewOffer(TestUtils.createOffer()));

        Assertions.assertEquals(TestUtils.createDaoException().getMessage(), error.getMessage());
        Mockito.verify(jdbcTemplate, Mockito.times(1))
                .update(Mockito.eq(query), Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.any(), Mockito.any());
    }

    @Test
    void updateOffer() throws DaoException {
        String query = "UPDATE t_offers SET lib_off = ?,lib_des = ?,num_tick = ?,num_price = ? WHERE idt_off = ?";

        Mockito.when(this.jdbcTemplate.update(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(1);

        offersDao.updateOffer(TestUtils.createOffer());

        Mockito.verify(jdbcTemplate, Mockito.times(1))
                .update(Mockito.eq(query), Mockito.anyString(), Mockito.anyString(), Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    void updateOfferWithError() {
        String query = "UPDATE t_offers SET lib_off = ?,lib_des = ?,num_tick = ?,num_price = ? WHERE idt_off = ?";

        Mockito.when(this.jdbcTemplate.update(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenThrow(new DataAccessException("error") {});

        DaoException error = Assertions.assertThrows(DaoException.class, () -> offersDao.updateOffer(TestUtils.createOffer()));

        Assertions.assertEquals(TestUtils.createDaoExceptionExecutingQuery().getMessage(), error.getMessage());
        Mockito.verify(jdbcTemplate, Mockito.times(1))
                .update(Mockito.eq(query), Mockito.anyString(), Mockito.anyString(), Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    void updateOfferNotFound() {
        String query = "UPDATE t_offers SET lib_off = ?,lib_des = ?,num_tick = ?,num_price = ? WHERE idt_off = ?";

        Mockito.when(this.jdbcTemplate.update(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(0);

        DaoException error = Assertions.assertThrows(DaoException.class, () -> offersDao.updateOffer(TestUtils.createOffer()));

        Assertions.assertEquals(TestUtils.createDaoExceptionOfferToUpdateNotFound().getMessage(), error.getMessage());
        Mockito.verify(jdbcTemplate, Mockito.times(1))
                .update(Mockito.eq(query), Mockito.anyString(), Mockito.anyString(), Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    void deleteOffer() throws DaoException {
        String query = "DELETE FROM t_offers WHERE idt_off = ?";

        Mockito.when(this.jdbcTemplate.update(Mockito.anyString(), Mockito.any(UUID.class))).thenReturn(1);

        offersDao.deleteOffer(TestUtils.createOffer().getUuid());

        Mockito.verify(jdbcTemplate, Mockito.times(1)).update(Mockito.eq(query), Mockito.any(UUID.class));
    }

    @Test
    void deleteOfferWithError() {
        String query = "DELETE FROM t_offers WHERE idt_off = ?";

        Mockito.when(this.jdbcTemplate.update(Mockito.anyString(), Mockito.any(UUID.class))).thenThrow(new DataAccessException("error") {});

        DaoException error = Assertions.assertThrows(DaoException.class, () -> offersDao.deleteOffer(TestUtils.createOffer().getUuid()));

        Assertions.assertEquals(TestUtils.createDaoExceptionExecutingQuery().getMessage(), error.getMessage());
        Mockito.verify(jdbcTemplate, Mockito.times(1)).update(Mockito.eq(query), Mockito.any(UUID.class));
    }

    @Test
    void deleteOfferNotFound() {
        String query = "DELETE FROM t_offers WHERE idt_off = ?";

        Mockito.when(this.jdbcTemplate.update(Mockito.anyString(), Mockito.any(UUID.class))).thenReturn(0);

        DaoException error = Assertions.assertThrows(DaoException.class, () -> offersDao.deleteOffer(TestUtils.createOffer().getUuid()));

        Assertions.assertEquals(TestUtils.createDaoExceptionOfferToDeleteNotFound().getMessage(), error.getMessage());
        Mockito.verify(jdbcTemplate, Mockito.times(1)).update(Mockito.eq(query), Mockito.any(UUID.class));
    }
}
