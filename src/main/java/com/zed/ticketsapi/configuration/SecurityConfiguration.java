package com.zed.ticketsapi.configuration;

import com.zed.ticketsapi.constants.PathConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(request -> request
                                                .requestMatchers(PathConstants.ALL_SWAGGER_UI_PATH,
                                                                 PathConstants.API_DOCS_PATH,
                                                                 PathConstants.ALL_API_DOCS_PATH).permitAll()
                                                .anyRequest().authenticated());

        http.oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()));

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
