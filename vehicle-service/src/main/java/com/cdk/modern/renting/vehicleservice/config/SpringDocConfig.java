package com.cdk.modern.renting.vehicleservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
@SecurityScheme(
    type = SecuritySchemeType.HTTP,
    name = "BearerAuthentication",
    scheme = "bearer")
public class SpringDocConfig {

  @Bean
  public OpenAPI baseOpenAPI() {
    return new OpenAPI()
        .addSecurityItem(new SecurityRequirement().addList("BearerAuthentication"))
        .info(
            new Info()
                .title("Modern Renting Vehicle Service")
                .description("SpringBoot 3.x application")
                .version("v0.0.1"));
  }

}
