package com.zed.ticketsapi.controller.rest.models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtKeycloak {
    private final String access_token;
    private final int expires_in;
    private final int refresh_expires_in;
    private final String refresh_token;
    private final String token_type;
    private final int not_before_policy;
    private final String session_state;
    private final String scope;
}
