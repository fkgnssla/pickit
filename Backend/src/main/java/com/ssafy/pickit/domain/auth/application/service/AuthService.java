package com.ssafy.pickit.domain.auth.application.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ssafy.pickit.domain.auth.dto.SignUpRequest;
import com.ssafy.pickit.domain.auth.dto.TokenResponse;
import com.ssafy.pickit.domain.member.application.service.MemberService;
import com.ssafy.pickit.domain.member.domain.Member;
import com.ssafy.pickit.domain.member.domain.Role;
import com.ssafy.pickit.domain.wallet.application.service.WalletService;
import com.ssafy.pickit.domain.wallet.domain.Wallet;
import com.ssafy.pickit.global.jwt.utils.JwtConstants;
import com.ssafy.pickit.global.jwt.utils.JwtUtils;
import com.ssafy.pickit.global.response.ApiResponse;
import com.ssafy.pickit.global.response.ResponseUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	@Value("${kakao.user-info-url}")
	private String KAKAO_USER_INFO_URL;
	private final RestTemplate restTemplate;
	private final MemberService memberService;
	private final WalletService walletService;

	// 메인 로그인 처리
	public ApiResponse<?> login(String token) {
		Map<String, Object> userInfo = getUserInfoFromKakao(token);

		Member findMember = memberService.findBySocialId(userInfo.get("id").toString());

		// 신규 회원일 때
		if (findMember == null) {
			return handleNewMember(userInfo.get("id").toString());
		}

		// 기존 회원일 때, JWT 발급
		return handleExistingMember(findMember);
	}

	// 카카오 API에서 사용자 정보 가져오기
	private Map<String, Object> getUserInfoFromKakao(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		HttpEntity<String> entity = new HttpEntity<>("", headers);

		ResponseEntity<Map> response = restTemplate.exchange(KAKAO_USER_INFO_URL, HttpMethod.GET, entity, Map.class);
		return response.getBody();
	}

	// 신규 회원 처리 로직
	private ApiResponse<?> handleNewMember(String socialId) {
		return ResponseUtils.success("socialId", socialId);
	}

	// 기존 회원 처리 로직 (JWT 발급)
	private ApiResponse<?> handleExistingMember(Member findMember) {
		Map<String, Object> claims = buildClaims(findMember);

		String accessToken = JwtUtils.generateToken(claims, JwtConstants.ACCESS_EXP_TIME);
		String refreshToken = JwtUtils.generateToken(claims, JwtConstants.REFRESH_EXP_TIME);

		return ResponseUtils.success(TokenResponse.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build());
	}

	// JWT Claims 생성
	private Map<String, Object> buildClaims(Member member) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("id", member.getId());
		claims.put("name", member.getName());
		claims.put("role", member.getRole());
		return claims;
	}

	public ApiResponse<?> signUp(SignUpRequest signUpRequest) {
		Wallet newWallet = walletService.create(signUpRequest.getAddress());

		Member newMember = Member.builder()
			.name(signUpRequest.getName())
			.age(signUpRequest.getAge())
			.gender(signUpRequest.getGender()).socialId(signUpRequest.getSocialId())
			.wallet(newWallet)
			.role(Role.MEMBER).build();

		memberService.create(newMember);

		return handleExistingMember(newMember);
	}
}
