package com.ronaldogoncalves.coopvote.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo("1.0"));
    }

    private Info apiInfo(String version) {
        return new Info().title("Coopvote assembly's, voting")
                .description("Coopvote assembly's, voting")
                .version(version);
    }

}