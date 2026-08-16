package it.financemanager.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
  @Bean
  OpenAPI financeManagerOpenApi() {
    String scheme = "bearerAuth";
    return new OpenAPI()
        .info(new Info()
                  .title("Finance Manager API")
                  .version("v1")
                  .description("Personal finance management API"))
        .addSecurityItem(new SecurityRequirement().addList(scheme))
        .components(new Components().addSecuritySchemes(
            scheme, new SecurityScheme()
                        .name(scheme)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")));
  }
}
