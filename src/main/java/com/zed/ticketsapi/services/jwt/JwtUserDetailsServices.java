package com.zed.ticketsapi.services.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class JwtUserDetailsServices {

    public Jwt getJwtToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof Jwt jwt) {
            return jwt;
        }
        return null;
    }

    public UUID getUuidUser() {
        Jwt jwtToken = getJwtToken();
        if (jwtToken != null) {
            return UUID.fromString(jwtToken.getSubject());
        }
        return null;
    }

    public String getEmailUser() {
        Jwt jwtToken = getJwtToken();
        if (jwtToken != null) {
            return jwtToken.getClaimAsString("email");
        }
        return null;
    }
}
