package com.zed.ticketsapi.mapper;

import com.zed.ticketsapi.constants.QueryDbConstants;
import com.zed.ticketsapi.controller.rest.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .uuid(UUID.fromString(rs.getString(QueryDbConstants.USER_ID)))
                .email(rs.getString(QueryDbConstants.USER_MAIL))
                .build();
    }
}