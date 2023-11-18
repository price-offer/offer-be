package com.offer.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "Offer BE API 명세서",
                description = "Offer 백엔드 API 명세서",
                version = "v1"))
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Server server = new Server();
        server.setUrl("https://offer-be.kro.kr");
        return new OpenAPI()
            .servers(List.of(server))
            .components(new Components()
                .addSecuritySchemes("jwt",
                    new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"))
            );
    }
}
