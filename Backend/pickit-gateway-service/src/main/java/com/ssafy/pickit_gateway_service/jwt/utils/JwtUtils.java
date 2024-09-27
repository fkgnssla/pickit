package com.ssafy.pickit_gateway_service.jwt.utils;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import com.ssafy.pickit_gateway_service.jwt.exception.CustomExpiredJwtException;
import com.ssafy.pickit_gateway_service.jwt.exception.CustomJwtException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtUtils {

	public static String secretKey = JwtConstants.key;

	public static String getTokenFromHeader(String header) {
		return header.split(" ")[1];
	}

	public static String extractUserId(String token) {
		SecretKey key = Keys.hmacShaKeyFor(JwtUtils.secretKey.getBytes(StandardCharsets.UTF_8));

		Map<String, Object> claim = Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token) // 파싱 및 검증, 실패 시 에러
			.getBody();

		return claim.get("id").toString();
	}


	public static Map<String, Object> validateToken(String token) {
		try {
			SecretKey key = Keys.hmacShaKeyFor(JwtUtils.secretKey.getBytes(StandardCharsets.UTF_8));

			Map<String, Object> claim = Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token) // 파싱 및 검증, 실패 시 에러
				.getBody();
			return claim;
		} catch (ExpiredJwtException expiredJwtException) {
			log.error("만료된 토큰입니다.");
			throw new CustomExpiredJwtException("만료된 토큰입니다.", expiredJwtException);
		} catch (Exception e) {
			log.error("존재하지 않는 토큰입니다.");
			throw new CustomJwtException("존재하지 않는 토큰입니다.");
		}
	}

	// 토큰이 만료되었는지 판단하는 메서드
	public static boolean isExpired(String token) {
		try {
			validateToken(token);
		} catch (Exception e) {
			return (e instanceof CustomExpiredJwtException);
		}
		return false;
	}

	// 토큰의 남은 만료시간 계산(분)
	public static long tokenRemainTime(long expTime) {
		return expTime / (1000 * 60);
	}

	// 토큰의 남은 만료시간 계산(초)
	public static long tokenRemainSeconds(long expTime) {
		return expTime / 1000;
	}

	public Map<String, Object> getUserDetailsFromToken(String token) {
		return validateToken(token);
	}
}
