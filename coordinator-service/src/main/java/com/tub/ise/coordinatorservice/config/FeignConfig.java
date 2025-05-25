package com.tub.ise.coordinatorservice.config;


import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            // Add common headers if needed
            template.header("Content-Type", "application/json");
            template.header("Accept", "application/json");
        };
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return (methodKey, response) -> {
            return switch (response.status()) {
                case 400 -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Bad request to service: " + response.request().url()
                );
                case 404 -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Service not available: " + response.request().url()
                );
                case 500 -> new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Service error: " + response.request().url()
                );
                default -> new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Service communication failed"
                );
            };
        };
    }
}
