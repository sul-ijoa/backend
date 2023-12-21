package com.teamB.sulijoa_be.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
						.allowedOrigins("http://localhost:3000")  // 허용할 도메인을 명시적으로 설정
						.allowedMethods("GET", "POST", "PUT", "DELETE")
						.allowedHeaders("Origin", "Content-Type", "Accept", "Authorization")
						.allowCredentials(true)
						.maxAge(3000);
	}
}
