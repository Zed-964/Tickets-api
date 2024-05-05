package com.zed.ticketsapi.controller.rest.models.ticket;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zed.ticketsapi.constants.ErrorConstants;
import com.zed.ticketsapi.constants.GenericConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Builder
public class Ticket {

    @JsonProperty("uuid")
    @Pattern(regexp = GenericConstants.PATTERN_UUID)
    @NotBlank(message = ErrorConstants.UUID_PATTERN_ERROR)
    private String uuid;

    @JsonProperty("firstname")
    @NotBlank(message = ErrorConstants.FIRSTNAME_EMPTY)
    private String firstname;

    @JsonProperty("lastname")
    @NotBlank(message = ErrorConstants.LASTNAME_EMPTY)
    private String lastname;

    @JsonProperty("date")
    @DateTimeFormat
    @NotBlank(message = ErrorConstants.UUID_PATTERN_ERROR)
    private String date;

    @JsonProperty("userId")
    @Pattern(regexp = GenericConstants.PATTERN_UUID)
    @NotBlank(message = ErrorConstants.UUID_PATTERN_ERROR)
    private String userId;
}
