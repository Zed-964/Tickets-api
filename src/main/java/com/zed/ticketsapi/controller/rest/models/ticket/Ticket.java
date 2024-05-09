package com.zed.ticketsapi.controller.rest.models.ticket;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zed.ticketsapi.constants.ErrorsConstants;
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
    @NotBlank(message = ErrorsConstants.UUID_PATTERN_ERROR)
    private String uuid;

    @JsonProperty("firstname")
    @NotBlank(message = ErrorsConstants.FIRSTNAME_EMPTY)
    private String firstname;

    @JsonProperty("lastname")
    @NotBlank(message = ErrorsConstants.LASTNAME_EMPTY)
    private String lastname;

    @JsonProperty("date")
    @DateTimeFormat
    @NotBlank(message = ErrorsConstants.UUID_PATTERN_ERROR)
    private String date;

    @JsonProperty("userId")
    @Pattern(regexp = GenericConstants.PATTERN_UUID)
    @NotBlank(message = ErrorsConstants.UUID_PATTERN_ERROR)
    private String userId;
}
