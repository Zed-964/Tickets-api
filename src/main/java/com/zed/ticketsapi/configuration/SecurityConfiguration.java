package com.zed.ticketsapi.configuration;

import com.zed.ticketsapi.constants.PathConstants;
import com.zed.ticketsapi.services.jwt.JwtConverterServices;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtConverterServices jwtConverterServices;

    private final UnauthorizedConfiguration unauthorizedConfiguration;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.oauth2ResourceServer(oauth -> oauth.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtConverterServices)));

        http.authorizeHttpRequests(request -> request
                                                .requestMatchers(HttpMethod.GET, "/offers").permitAll()
                                                .requestMatchers(PathConstants.ALL_SWAGGER_UI_PATH,
                                                                 PathConstants.API_DOCS_PATH,
                                                                 PathConstants.ALL_API_DOCS_PATH
                                                ).permitAll()
                .anyRequest().authenticated());

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.cors(cors -> cors.configurationSource(configCors()));

        http.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedConfiguration));

        return http.build();
    }

    CorsConfigurationSource configCors() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}