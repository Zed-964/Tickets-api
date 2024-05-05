package com.zed.ticketsapi.configuration;

import com.zed.ticketsapi.constants.GenericConstants;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@OpenAPIDefinition(
    info = @Info(
        contact = @Contact(
            name = "Developer",
            email = "enzotechnic@gmail.com"
        ),
        description = "OpenApi documentation for Tickets-Api",
        title = "Tickets-Api",
        version = "1.0.0",
        license = @License(
            name = "MIT"
        )
    ),
    tags = {
        @Tag(
            name = GenericConstants.OFFER_TAG,
            description = GenericConstants.OFFER_DESCRIPTION
        ),
        @Tag(
            name = GenericConstants.TICKET_TAG,
            description = GenericConstants.TICKET_DESCRIPTION
        )
    }
)
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SwaggerConfiguration implements WebMvcConfigurer {

}
