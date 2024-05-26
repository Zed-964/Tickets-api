package com.zed.ticketsapi.controller.rest.models.tickets;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zed.ticketsapi.constants.ErrorsConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Builder
public class Ticket {

    @JsonProperty("uuid")
    @NotNull(message = ErrorsConstants.UUID_PATTERN_ERROR)
    private UUID uuid;

    @JsonProperty("firstname")
    @NotBlank(message = ErrorsConstants.FIRSTNAME_EMPTY)
    private String firstname;

    @JsonProperty("lastname")
    @NotBlank(message = ErrorsConstants.LASTNAME_EMPTY)
    private String lastname;

    @JsonProperty("date")
    @DateTimeFormat
    @NotBlank(message = ErrorsConstants.UUID_PATTERN_ERROR)
    private LocalDate date;

    @JsonProperty("userId")
    @NotNull(message = ErrorsConstants.UUID_PATTERN_ERROR)
    private UUID userId;
}
