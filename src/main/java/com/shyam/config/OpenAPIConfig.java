package com.shyam.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
    info = @Info(
        title = "Spring Boot Rest API",
        version = "1.0",
        description = "Demo api with authentication and authorization",
        contact = @Contact(
            name = "Karnam Shyam",
            email = "karnamshyam9009@gmail.com",
            url = "https://karnamshyam1947.github.io/portfolio/"
        )
    ),
    security = {
        @SecurityRequirement(name = "bearerAuth")
    },
    servers = {
        @Server(
            url = "http://localhost:8080/",
            description = "development server(test)"
        ),
        @Server(
            url = "http://localhost:8080/",
            description = "test server(test)"
        ),
        @Server(
            url = "http://localhost:8080/",
            description = "production server(test)"
        )
    }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT authentication",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenAPIConfig {
    
}
