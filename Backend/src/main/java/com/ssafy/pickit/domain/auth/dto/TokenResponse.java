package com.ssafy.pickit.domain.auth.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TokenResponse {
	String accessToken;
	String refreshToken;
}
