package com.zed.ticketsapi.controller.utils;

import com.zed.ticketsapi.controller.rest.models.ApiError;
import com.zed.ticketsapi.controller.rest.models.ApiErrorResponse;
import com.zed.ticketsapi.controller.rest.models.ApiErrorSimple;
import com.zed.ticketsapi.controller.rest.models.ApiTicketsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
public class ErrorUtils {
    private ErrorUtils() {

    }

    public static ResponseEntity<ApiTicketsResponse> thrownApiError(ApiError e) {
        ApiErrorResponse errorResponse;
        errorResponse = ApiErrorResponse.builder().data(new ApiErrorSimple(e)).build();
        return new ResponseEntity<>(errorResponse, e.getCode());
    }

    public static ResponseEntity<ApiTicketsResponse> thrownFormatUuidInvalid() {
        ApiErrorResponse errorResponse;
        ApiError e;

        log.error("Format of the uuid is invalid");
        e = ApiError.builder().message("Format of the uuid is invalid").code(HttpStatus.BAD_REQUEST).build();
        errorResponse = ApiErrorResponse.builder().data(new ApiErrorSimple(e)).build();

        return new ResponseEntity<>(errorResponse, e.getCode());
    }
}
