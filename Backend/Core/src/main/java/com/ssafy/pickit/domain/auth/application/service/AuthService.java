package com.ssafy.pickit.domain.auth.application.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ssafy.pickit.domain.auth.dto.LoginResponse;
import com.ssafy.pickit.domain.auth.dto.SignUpRequest;
import com.ssafy.pickit.domain.auth.exception.KakaoAPIException;
import com.ssafy.pickit.domain.member.application.service.MemberService;
import com.ssafy.pickit.domain.member.domain.Member;
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
		String socialId = userInfo.get("id").toString();

		Member findMember = memberService.findBySocialId(socialId);

		return (findMember != null) ? handleExistingMember(findMember) : handleNewMember(socialId);
	}

	// 카카오 API에서 사용자 정보 가져오기
	private Map<String, Object> getUserInfoFromKakao(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		HttpEntity<String> entity = new HttpEntity<>("", headers);

		ResponseEntity<Map> response = restTemplate.exchange(KAKAO_USER_INFO_URL, HttpMethod.GET, entity, Map.class);
		if (!response.getStatusCode().is2xxSuccessful()) {
			throw new KakaoAPIException("카카오 자원 서버 API 요청 실패");
		}

		if (response.getBody() == null || response.getBody().isEmpty()) {
			throw new KakaoAPIException("카카오 자원 서버 API 요청 실패");
		}
		return response.getBody();
	}

	// 신규 회원 처리 로직
	private ApiResponse<?> handleNewMember(String socialId) {
		return ResponseUtils.success(LoginResponse.of(null, false, socialId, null, null));
	}

	// 기존 회원 처리 로직 (JWT 발급)
	private ApiResponse<?> handleExistingMember(Member findMember) {
		Map<String, Object> claims = buildClaims(findMember);

		String accessToken = JwtUtils.generateToken(claims, JwtConstants.ACCESS_EXP_TIME);
		String refreshToken = JwtUtils.generateToken(claims, JwtConstants.REFRESH_EXP_TIME);

		return ResponseUtils.success(LoginResponse.of(findMember.getId(), true, null, accessToken, refreshToken));
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
		Wallet newWallet = walletService.create(signUpRequest.address());

		String birthday = signUpRequest.birthday();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

		Member newMember = Member.of(signUpRequest, newWallet, LocalDate.parse(birthday, formatter));
		memberService.create(newMember);

		return handleExistingMember(newMember);
	}
}
