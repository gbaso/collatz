package com.example.collatz;

import org.springframework.boot.context.properties.ConfigurationProperties;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@ConfigurationProperties("service")
public record ServiceProperties(ServiceType type) {

    @Getter
    @AllArgsConstructor
    public enum ServiceType {
        SIMPLE("simple"), CACHED("cached");
        @Nonnull
        private final String name;
    }

}
