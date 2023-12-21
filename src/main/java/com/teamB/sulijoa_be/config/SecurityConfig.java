package com.teamB.sulijoa_be.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	// 비밀번호 암호화
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Security Filter Chain
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
						.authorizeRequests(authorizeRequests ->
										authorizeRequests
														.requestMatchers("/**").permitAll()
														.anyRequest().authenticated()
						)
//						.sessionManagement(sessionManagement ->
//										sessionManagement.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//						)
						.cors().and()
						.csrf().disable();

		return http.build();
	}
}