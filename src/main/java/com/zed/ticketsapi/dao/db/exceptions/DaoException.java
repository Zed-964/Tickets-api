package com.zed.ticketsapi.dao.db.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DaoException extends Exception {


    private final HttpStatus code;

    public DaoException(String message, Throwable cause, HttpStatus code) {
        super(message, cause);
        this.code = code;
    }

    public DaoException(String message, HttpStatus code) {
        super(message);
        this.code = code;
    }
}
