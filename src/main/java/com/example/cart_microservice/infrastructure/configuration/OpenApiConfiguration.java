package com.example.cart_microservice.infrastructure.configuration;

import com.example.cart_microservice.infrastructure.utils.InfrastructureConstants;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = InfrastructureConstants.BEARER_TOKEN,
        type = SecuritySchemeType.HTTP,
        scheme = InfrastructureConstants.BEARER,
        bearerFormat = InfrastructureConstants.JWT
)
public class OpenApiConfiguration {
}
