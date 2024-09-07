package com.ssafy.pickit.domain.auth.api;

import com.ssafy.pickit.domain.auth.dto.SignUpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pickit.domain.auth.application.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<?> kakaoLogin(@RequestParam("token") String token) {
		return authService.login(token);
	}

	@PostMapping("/sign-up")
	public ResponseEntity<?> signup(@RequestBody SignUpRequest signUpRequest) {
		return authService.signUp(signUpRequest);
	}

}

