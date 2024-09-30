package com.ssafy.pickit.global.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.ssafy.pickit.global.error.LoggableException;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebHookUtils {

	private static final RestTemplate restTemplate = new RestTemplate();

	private static String baseUrl;
	private static String CONTENT_TYPE = "application/json";

	@Value("${mm-logger.base-url}")
	private String baseUrlProperty;

	@PostConstruct
	private void init() {
		WebHookUtils.baseUrl = baseUrlProperty;
	}

	public static void sendWebHookMessage(String message) {

		HttpHeaders headers = new HttpHeaders();
		headers.set(CONTENT_TYPE, CONTENT_TYPE);

		String body = "{\"text\":\"" + message + "\"}";

		HttpEntity<String> entity = new HttpEntity<>(body, headers);
		try {
			restTemplate.exchange(baseUrl, HttpMethod.POST, entity, String.class);
		} catch (HttpClientErrorException e) {
			throw new LoggableException("webhook 실패");
		}
	}
}
