package com.example.collatz.config;

import jakarta.annotation.Nonnull;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;

@ConfigurationProperties("service")
public record ServiceProperties(ServiceType type) {

    @Getter
    @AllArgsConstructor
    public enum ServiceType {
        SIMPLE("simple"), CACHED("cached"), PARALLEL("parallel");

        @Nonnull
        private final String name;
    }

}
