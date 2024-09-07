package com.ssafy.pickit.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ssafy.pickit.global.jwt.filter.JwtVerifyFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JwtVerifyFilter jwtVerifyFilter() {
		return new JwtVerifyFilter();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(AbstractHttpConfigurer::disable);

		http.sessionManagement(httpSecuritySessionManagementConfigurer -> {
			httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		});

		http.authorizeHttpRequests(authorize -> authorize
			.requestMatchers("/plan/**").hasAnyRole("USER") // 인가 테스트용
			.anyRequest().permitAll());

		http.addFilterBefore(jwtVerifyFilter(), UsernamePasswordAuthenticationFilter.class);

		http.formLogin(AbstractHttpConfigurer::disable);

		return http.build();
	}
}
