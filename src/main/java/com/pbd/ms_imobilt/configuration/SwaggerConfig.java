package com.pbd.ms_imobilt.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Imobilt")
                        .description(
                                "Project of a microservice for management of land lot sales."
                        )
                        .version("1.0.0v"))
                .externalDocs(new ExternalDocumentation()
                        .description("Link to the project repository.")
                        .url("https://github.com/PBD-2024-1-Imobilt/ms_imobilt"));

    }
}
