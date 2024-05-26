package com.zed.ticketsapi.dao.db.users.impl;

import com.zed.ticketsapi.constants.GenericConstants;
import com.zed.ticketsapi.constants.QueryDbConstants;
import com.zed.ticketsapi.controller.rest.models.User;
import com.zed.ticketsapi.dao.db.exceptions.DaoException;
import com.zed.ticketsapi.dao.db.users.UsersDao;
import com.zed.ticketsapi.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UsersDaoImpl implements UsersDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public User getUser(UUID uuid) throws DaoException {
        User result;

        String query = QueryDbConstants.SELECT + QueryDbConstants.FROM + QueryDbConstants.TABLE_USER +
                QueryDbConstants.WHERE + QueryDbConstants.USER_ID_EQUAL + QueryDbConstants.QUESTION_MARK;

        try {
            result = jdbcTemplate.queryForObject(query, new UserMapper(), uuid);
        } catch (EmptyResultDataAccessException exception) {
            log.warn("User not found ! Uuid : {}", uuid);
            return null;
        } catch (Exception e) {
            log.error(GenericConstants.LOG_QUERY_DAO, e.getMessage(), e.getCause());
            throw new DaoException(GenericConstants.MSG_ERROR_DAO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return result;
    }

    @Override
    public void insertNewUser(User user) throws DaoException {
        int result;

        String query = QueryDbConstants.INSERT_INTO + QueryDbConstants.TABLE_USER + QueryDbConstants.FORMAT_USER
                + QueryDbConstants.VALUES_2;

        try {
            result = jdbcTemplate.update(query, user.getUuid(), user.getEmail());
        } catch (Exception e) {
            log.error(GenericConstants.LOG_QUERY_DAO, e.getMessage(), e.getCause());
            throw new DaoException(GenericConstants.MSG_ERROR_DAO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (result == 0) {
            log.error("Nothing has been created !");
            throw new DaoException("An error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("Insert User Successfully !");
    }
}
