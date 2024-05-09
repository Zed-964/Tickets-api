package com.zed.ticketsapi.controller.rest.models.ticket;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zed.ticketsapi.constants.ErrorsConstants;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TicketSimple {

    @JsonProperty("firstname")
    @NotBlank(message = ErrorsConstants.FIRSTNAME_EMPTY)
    private String firstname;

    @JsonProperty("lastname")
    @NotBlank(message = ErrorsConstants.LASTNAME_EMPTY)
    private String lastname;
}
