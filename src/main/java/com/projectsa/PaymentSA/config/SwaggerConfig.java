package com.projectsa.PaymentSA.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI producerAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Producer API - ProducerSA")
                        .description("API responsible for sending transfer request messages to RabbitMQ")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Samuel Almeida de Sa")
                                .url("https://github.com/samuelsadev/ProducerSA")
                                .email("samuelsa.dev@gmail.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("Complete documentation on GitHub")
                        .url("https://github.com/samuelsadev/CenterSA"));
    }

    @Bean
    public OpenApiCustomizer globalApiResponsesCustomizer() {
        return openApi -> openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {

            ApiResponses apiResponses = operation.getResponses();

            apiResponses.addApiResponse("200", createApiResponse("Operation successfully completed!"));
            apiResponses.addApiResponse("201", createApiResponse("Resource successfully created!"));
            apiResponses.addApiResponse("204", createApiResponse("Resource successfully deleted!"));
            apiResponses.addApiResponse("400", createApiResponse("Bad request! Please check the provided data."));
            apiResponses.addApiResponse("401", createApiResponse("Unauthorized! Invalid or missing token."));
            apiResponses.addApiResponse("403", createApiResponse("Forbidden! Insufficient permissions."));
            apiResponses.addApiResponse("404", createApiResponse("Resource not found!"));
            apiResponses.addApiResponse("500", createApiResponse("Internal server error!"));
        }));
    }

    private ApiResponse createApiResponse(String message) {
        return new ApiResponse().description(message);
    }
}
