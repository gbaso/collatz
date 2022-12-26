package com.example.collatz.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("parallel")
public record ParallelProperties(int level) {}
