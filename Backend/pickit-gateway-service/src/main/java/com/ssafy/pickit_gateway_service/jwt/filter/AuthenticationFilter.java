package com.ssafy.pickit_gateway_service.jwt.filter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.pickit_gateway_service.jwt.utils.JwtConstants;
import com.ssafy.pickit_gateway_service.jwt.utils.JwtUtils;
import com.ssafy.pickit_gateway_service.jwt.utils.RouteValidator;
import com.ssafy.pickit_gateway_service.response.ResponseUtils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

	@Autowired
	private RouteValidator validator;

	@Autowired
	private ObjectMapper objectMapper;

	public AuthenticationFilter() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {

		// pre
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			ServerHttpResponse response = exchange.getResponse();

			log.info("Request = " + request.getURI().getPath());

			if (validator.isSecured.test(request)) {
				List<String> authHeaders = request.getHeaders().get(HttpHeaders.AUTHORIZATION);

				if (authHeaders == null || authHeaders.isEmpty()) {
					log.error("헤더에 토큰이 존재하지 않습니다.");
					return handleUnauthorizedError(response, "헤더에 토큰이 존재하지 않습니다.");
				}

				String authHeader = authHeaders.get(0);

				if (authHeader.startsWith(JwtConstants.JWT_TYPE)) {
					String token = authHeader.substring(7);

					try {
						JwtUtils.validateToken(token);
					} catch (Exception e) {
						log.error("토큰이 유효하지 않습니다.");
						return handleUnauthorizedError(response, "토큰이 유효하지 않습니다.");
					}

					return chain.filter(exchange);
				} else {
					log.error("잘못된 토큰입니다.");
					return handleUnauthorizedError(response, "잘못된 토큰입니다.");
				}
			}

			// post
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				log.info("Custom Post filter: response code: " + response.getStatusCode());
			}));
		};
	}

	private Mono<Void> handleUnauthorizedError(ServerHttpResponse response, String errorMessage) {
		response.setStatusCode(HttpStatus.UNAUTHORIZED);
		response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

		try {
			String errorJson = objectMapper.writeValueAsString(
				ResponseUtils.error(HttpStatus.UNAUTHORIZED, errorMessage));

			DataBuffer dataBuffer = response.bufferFactory().wrap(errorJson.getBytes());

			return response.writeWith(Mono.just(dataBuffer));
		} catch (Exception e) {
			log.error("Error processing the unauthorized error response", e);
			return Mono.error(e);
		}
	}

	@Data
	public static class Config {
		private String baseMessage;
		private boolean preLogger;
		private boolean postLogger;
	}
}