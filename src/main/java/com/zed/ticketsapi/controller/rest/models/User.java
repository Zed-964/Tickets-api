package com.zed.ticketsapi.controller.rest.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class User {

    @JsonProperty("uuid")
    private final UUID uuid;

    @JsonProperty("email")
    private final String email;
}
