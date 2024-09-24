package com.ssafy.pickit.domain.auth.dto;

public record SignUpRequest(
	String name,
	Integer age,
	String gender,
	String socialId,
	String address
) {
}
