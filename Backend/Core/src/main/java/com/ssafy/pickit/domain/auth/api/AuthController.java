package com.ssafy.pickit.domain.auth.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pickit.domain.auth.application.service.AuthService;
import com.ssafy.pickit.domain.auth.dto.LoginRequest;
import com.ssafy.pickit.domain.auth.dto.SignUpRequest;
import com.ssafy.pickit.global.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "AuthController", description = "로그인/회원가입 API")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@Operation(summary = "카카오 소셜 로그인", description = "카카오 자원 서버에 token을 사용하여 회원 정보를 요청합니다.")
	@PostMapping("/login")
	public ApiResponse<?> kakaoLogin(@RequestBody LoginRequest loginRequest) {
		return authService.login(loginRequest.token());
	}

	@Operation(summary = "회원 가입", description = "추가 정보를 사용하여 회원가입합니다.")
	@PostMapping("/sign-up")
	public ApiResponse<?> signup(@RequestBody SignUpRequest signUpRequest) {
		return authService.signUp(signUpRequest);
	}

}

