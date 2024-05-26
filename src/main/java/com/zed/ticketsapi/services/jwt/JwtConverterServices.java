package com.zed.ticketsapi.services.jwt;

import com.zed.ticketsapi.constants.ErrorsConstants;
import com.zed.ticketsapi.constants.GenericConstants;
import com.zed.ticketsapi.controller.rest.models.errors.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
public class JwtConverterServices implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        Collection<GrantedAuthority> authorities;
        try {
            authorities = Stream.concat(
                    jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                    extractResourceRoles(jwt).stream()
            ).collect(Collectors.toSet());
        } catch (ApiError e) {
            throw new RuntimeException(e);
        }

        return new JwtAuthenticationToken(
                jwt,
                authorities,
                jwt.getClaim(JwtClaimNames.SUB)
        );
    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) throws ApiError {
        Map<String, Object> resourceAccess;
        Map<String, Object> resource;
        Collection<String> resourceRoles;

        if (jwt.getClaim(GenericConstants.RESOURCE_ACCESS) == null) {
            log.error(ErrorsConstants.JWT_FORMAT_INCORRECT, jwt.getSubject());
            throw ApiError.builder().message(ErrorsConstants.JWT_UNAUTHORIZED).code(HttpStatus.UNAUTHORIZED).build();
        }
        resourceAccess = jwt.getClaim(GenericConstants.RESOURCE_ACCESS);

        if (resourceAccess.get(GenericConstants.RESOURCE_ID) == null) {
            log.error(ErrorsConstants.JWT_RESOURCE_ID_UNKNOWN, jwt.getSubject());
            throw ApiError.builder().message(ErrorsConstants.JWT_UNAUTHORIZED).code(HttpStatus.UNAUTHORIZED).build();
        }
        resource = (Map<String, Object>) resourceAccess.get(GenericConstants.RESOURCE_ID);

        resourceRoles = (Collection<String>) resource.get("roles");

        return resourceRoles
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());
    }
}
