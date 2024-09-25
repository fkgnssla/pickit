package com.ssafy.pickit.domain.auth.dto;

public record TokenResponse(
	Boolean isExist,
	String socialId,
	String accessToken,
	String refreshToken
) {
	public static TokenResponse of(Boolean isExist, String socialId, String accessToken, String refreshToken) {
		return new TokenResponse(isExist, socialId, accessToken, refreshToken);
	}
}
