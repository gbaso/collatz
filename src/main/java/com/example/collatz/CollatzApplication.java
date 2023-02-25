package com.example.collatz;

import java.util.List;
import java.util.Map;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.example.collatz.config.ParallelProperties;
import com.example.collatz.config.ServiceProperties;
import com.example.collatz.service.CollatzService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableConfigurationProperties({ ServiceProperties.class, ParallelProperties.class })
public class CollatzApplication {

    public static void main(String[] args) {
        SpringApplication.run(CollatzApplication.class, args);
    }

    @Bean
    ApplicationRunner runner(Map<String, CollatzService> serviceMap, ServiceProperties service) {
        return args -> {
            List<String> nonOptArgs = args.getNonOptionArgs();
            if (nonOptArgs.isEmpty()) {
                log.info("pass the starting number you dolt!");
                System.exit(1);
            }
            int max = Integer.parseInt(nonOptArgs.get(0));
            var collatzService = serviceMap.get(service.type().getName());
            var info = collatzService.findMaxLength(max);
            log.info("Max length {} for starting value(s): {}", info.length(), info.starts());
        };
    }

}
