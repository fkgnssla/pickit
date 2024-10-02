package com.ssafy.pickit.domain.auth.dto;

public record LoginResponse(
	Long memberId,
	Boolean isExist,
	String socialId,
	String accessToken,
	String refreshToken
) {
	public static LoginResponse of(Long memberId, Boolean isExist, String socialId, String accessToken,
		String refreshToken) {
		return new LoginResponse(memberId, isExist, socialId, accessToken, refreshToken);
	}
}
