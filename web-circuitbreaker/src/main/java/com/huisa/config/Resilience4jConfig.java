package com.huisa.config;

import java.time.Duration;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

@Configuration
public class Resilience4jConfig {
	
	@Bean
	public CircuitBreakerRegistry circuitBreakerRegistry() {
		  
		CircuitBreakerConfig config = CircuitBreakerConfig.custom()
				.slidingWindowSize(10)
		        .failureRateThreshold(50)
		        .waitDurationInOpenState(Duration.ofSeconds(10))
		        .recordExceptions(Exception.class)
		        .ignoreExceptions(IllegalArgumentException.class)
		        .build();
		
		return CircuitBreakerRegistry.of(Map.of("externalService",config));
	}
	
	public CircuitBreaker myServiceCircuitBreaker(CircuitBreakerRegistry circuitBreakerRegistry) {
		return circuitBreakerRegistry.circuitBreaker("externalService");
	}

}
