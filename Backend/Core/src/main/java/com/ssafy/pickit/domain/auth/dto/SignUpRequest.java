package com.ssafy.pickit.domain.auth.dto;

public record SignUpRequest(
	String name,
	String birthday,
	String gender,
	String socialId,
	String address
) {
}
