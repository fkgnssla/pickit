package com.ssafy.pickit.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("http://localhost:5173", "https://localhost:5173",
				"http://j11a309.p.ssafy.io:5173", "https://j11a309.p.ssafy.io:5173")
			.allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메소드 설정
			.allowedHeaders(CorsConfiguration.ALL)
			.allowCredentials(true) // 인증 정보 허용 여부
			.maxAge(3600L);
	}
}
