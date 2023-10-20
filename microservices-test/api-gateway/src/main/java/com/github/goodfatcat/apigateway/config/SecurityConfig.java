package com.github.goodfatcat.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
	
	@Bean
	SecurityWebFilterChain filterChain(ServerHttpSecurity serverHttpSecurity) {
		return serverHttpSecurity
				.csrf(csrf -> csrf.disable())
				.authorizeExchange(authorize -> authorize.pathMatchers("/eureka/**").permitAll()
						.anyExchange().authenticated())
				.oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
		.build();
	}
}
