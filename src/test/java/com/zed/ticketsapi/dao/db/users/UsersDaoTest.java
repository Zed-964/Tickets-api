package com.zed.ticketsapi.dao.db.users;

import com.zed.ticketsapi.controller.rest.models.User;
import com.zed.ticketsapi.dao.db.exceptions.DaoException;
import com.zed.ticketsapi.dao.db.users.impl.UsersDaoImpl;
import com.zed.ticketsapi.mapper.UserMapper;
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

import java.util.UUID;

@SpringBootTest
@DisabledInAotMode
class UsersDaoTest {

    @Mock
    JdbcTemplate jdbcTemplate;

    private UsersDaoImpl usersDao;

    @BeforeEach
    public void setUp() {
        usersDao = Mockito.spy(new UsersDaoImpl(jdbcTemplate));
    }

    @Test
    void getUser() throws DaoException {
        User expected = TestUtils.createUser();

        String queryExpected = "SELECT * FROM t_users WHERE idt_usr = ?";

        Mockito.when(jdbcTemplate.queryForObject(Mockito.eq(queryExpected), Mockito.any(UserMapper.class), Mockito.any()))
                .thenReturn(expected);

        var response = usersDao.getUser(UUID.fromString("31347b9a-542e-4f48-a7d2-3c30c330ccc0"));
        Assertions.assertEquals(expected, response);
        Mockito.verify(jdbcTemplate, Mockito.times(1))
                .queryForObject(Mockito.any(), Mockito.any(UserMapper.class), Mockito.any());
    }

    @Test
    void getUserNoExist() throws DaoException {
        String queryExpected = "SELECT * FROM t_users WHERE idt_usr = ?";

        Mockito.when(jdbcTemplate.queryForObject(Mockito.eq(queryExpected), Mockito.any(UserMapper.class), Mockito.any()))
                .thenThrow(new EmptyResultDataAccessException(0));

        var response = usersDao.getUser(UUID.fromString("31347b9a-542e-4f48-a7d2-3c30c330ccc0"));
        Assertions.assertNull(response);
        Mockito.verify(jdbcTemplate, Mockito.times(1))
                .queryForObject(Mockito.any(), Mockito.any(UserMapper.class), Mockito.any());
    }

    @Test
    void getUserWithError() {
        String queryExpected = "SELECT * FROM t_users WHERE idt_usr = ?";

        Mockito.when(jdbcTemplate.queryForObject(Mockito.eq(queryExpected), Mockito.any(UserMapper.class), Mockito.any()))
                .thenThrow((new DataAccessException("error") {}));

        DaoException error = Assertions.assertThrows(DaoException.class,
                () -> usersDao.getUser(UUID.fromString("31347b9a-542e-4f48-a7d2-3c30c330ccc0")));
        Assertions.assertEquals(TestUtils.createDaoExceptionExecutingQuery().getMessage(), error.getMessage());
        Mockito.verify(jdbcTemplate, Mockito.times(1))
                .queryForObject(Mockito.any(), Mockito.any(UserMapper.class), Mockito.any());
    }

    @Test
    void insertUser() throws DaoException {
        String query = "INSERT INTO t_users (idt_usr, mail) VALUES (?,?)";

        Mockito.when(this.jdbcTemplate.update(Mockito.anyString(), Mockito.any(), Mockito.anyString())).thenReturn(1);

        usersDao.insertNewUser(TestUtils.createUser());

        Mockito.verify(jdbcTemplate, Mockito.times(1)).update(Mockito.eq(query), Mockito.any(), Mockito.anyString());
    }

    @Test
    void insertUserWithError() {
        String query = "INSERT INTO t_users (idt_usr, mail) VALUES (?,?)";

        Mockito.when(this.jdbcTemplate.update(Mockito.anyString(), Mockito.any(), Mockito.anyString()))
                .thenThrow(new DataAccessException("error") {});

        DaoException error = Assertions.assertThrows(DaoException.class, () -> usersDao.insertNewUser(TestUtils.createUser()));

        Assertions.assertEquals(TestUtils.createDaoExceptionExecutingQuery().getMessage(), error.getMessage());
        Mockito.verify(jdbcTemplate, Mockito.times(1)).update(Mockito.eq(query), Mockito.any(), Mockito.anyString());
    }

    @Test
    void insertUserWith0Result() {
        String query = "INSERT INTO t_users (idt_usr, mail) VALUES (?,?)";

        Mockito.when(this.jdbcTemplate.update(Mockito.anyString(), Mockito.any(), Mockito.anyString())).thenReturn(0);

        DaoException error = Assertions.assertThrows(DaoException.class, () -> usersDao.insertNewUser(TestUtils.createUser()));

        Assertions.assertEquals(TestUtils.createDaoException().getMessage(), error.getMessage());
        Mockito.verify(jdbcTemplate, Mockito.times(1)).update(Mockito.eq(query), Mockito.any(), Mockito.anyString());

    }
}