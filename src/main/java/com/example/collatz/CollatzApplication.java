package com.example.collatz;

import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableConfigurationProperties(ServiceProperties.class)
public class CollatzApplication {

	public static void main(String[] args) {
		SpringApplication.run(CollatzApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(Map<String, CollatzService> serviceMap, ServiceProperties service) {
		return args -> {
			if (args.length < 1) {
				log.info("pass the starting number you dolt!");
				System.exit(1);
			}
			int max = Integer.parseInt(args[0]);
			var collatzService = serviceMap.get(service.type().getName());
			var info = collatzService.findMaxLength(max);
			log.info("Max length {} for starting value(s): {}", info.length(), info.starts());
		};
	}

}
