package com.ssafy.pickit_gateway_service.jwt.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtConstants {
	public static String key;

	public static String JWT_REFRESH_HEADER;

	public static final String JWT_TYPE = "Bearer ";

	@Value("${jwt.secretKey}")
	public void setKey(String secretKey) {
		this.key = secretKey;
	}

	@Value("${jwt.refresh.header}")
	public void setJwtRefreshHeader(String header) {
		this.JWT_REFRESH_HEADER = header;
	}
}
