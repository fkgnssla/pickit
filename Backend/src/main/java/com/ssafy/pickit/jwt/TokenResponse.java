package com.ssafy.pickit.jwt;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TokenResponse {
	String accessToken;
	String refreshToken;
}
