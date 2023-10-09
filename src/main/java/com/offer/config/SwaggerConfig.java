package com.offer.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "Offer BE API 명세서",
                description = "Offer 백엔드 API 명세서",
                version = "v1"))
@Configuration
public class SwaggerConfig {
}
