package com.github.goodfatcat.discoveryserver.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	@Value("${eureka.username}")
	private String username;
	@Value("${eureka.password}")
	private String password;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(request -> request.anyRequest().authenticated())
		.httpBasic(Customizer.withDefaults()).build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	UserDetailsService inMemoryDetailsService(PasswordEncoder passwordEncoder) {
		UserDetails userDetails = User.builder()
				.username(username)
				.password(password)
				.authorities("USER")
				.passwordEncoder(password -> passwordEncoder.encode(password))
				.build();
		return new InMemoryUserDetailsManager(userDetails);
	}
}
