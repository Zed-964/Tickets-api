package com.zed.ticketsapi.dao.db.users;

import com.zed.ticketsapi.controller.rest.models.User;
import com.zed.ticketsapi.dao.db.exceptions.DaoException;

import java.util.UUID;

public interface UsersDao {

    User getUser(UUID uuid) throws DaoException;

    void insertNewUser(User user) throws DaoException;

}
