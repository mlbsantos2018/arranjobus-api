package com.transporte.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Transport API",
        version = "v1.0.0",
        description = "API para gerenciamento de transporte de congressos e assembleias",
        contact = @Contact(
            name = "Transport API Support",
            email = "support@transportapi.com"
        )
    ),
    servers = {
        @Server(
            url = "http://localhost:8080",
            description = "Local Development Server"
        ),
        @Server(
            url = "https://api.transportapi.com",
            description = "Production Server"
        )
    }
)
@SecurityScheme(
    name = "Bearer Authentication",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
    description = "JWT token para autenticação. Use 'Bearer <token>'",
    in = SecuritySchemeIn.HEADER
)
public class OpenAPIConfig {
}
