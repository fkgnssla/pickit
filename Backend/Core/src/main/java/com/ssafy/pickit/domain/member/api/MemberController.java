package com.ssafy.pickit.domain.member.api;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pickit.domain.member.application.service.MemberService;
import com.ssafy.pickit.domain.member.domain.PrincipalDetail;
import com.ssafy.pickit.global.aop.LogExecution;
import com.ssafy.pickit.global.response.ApiResponse;
import com.ssafy.pickit.global.response.ResponseUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "MemberController", description = "회원 API")
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@LogExecution
public class MemberController {
	private final MemberService memberService;

	@Operation(summary = "내 정보 조회", description = "자신의 이름, 나이, 성별을 조회합니다.")
	@GetMapping
	public ApiResponse<?> findMyInfo(@AuthenticationPrincipal PrincipalDetail principalDetail) {
		return ResponseUtils.success(memberService.findMyInfo(principalDetail.getId()));
	}
}
