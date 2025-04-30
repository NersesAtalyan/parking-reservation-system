package com.parking.system.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiSpec() {
        return new OpenAPI()
            .info(new Info()
                .title("Parking Reservation System API")
                .description("API for managing community-based parking spots")
                .version("v1.0.0")
                .contact(new Contact()
                    .name("Nerses Atalyan")
                    .email("nerses.atalyan@gmail.com")
                )
                .license(new License()
                    .name("MIT License")
                    .url("https://opensource.org/licenses/MIT")
                )
            );
    }
}
