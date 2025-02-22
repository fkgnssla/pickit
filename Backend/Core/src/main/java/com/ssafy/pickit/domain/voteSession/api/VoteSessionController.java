package com.ssafy.pickit.domain.voteSession.api;

import java.util.HashMap;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pickit.domain.member.domain.PrincipalDetail;
import com.ssafy.pickit.domain.voteSession.application.service.VoteSessionService;
import com.ssafy.pickit.domain.voteSession.dto.VoteSessionRequest;
import com.ssafy.pickit.global.aop.LogExecution;
import com.ssafy.pickit.global.response.ApiResponse;
import com.ssafy.pickit.global.response.ResponseUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "VoteSessionController", description = "투표 세션 API")
@RestController
@RequestMapping("/vote-session")
@RequiredArgsConstructor
@LogExecution
public class VoteSessionController {
	private final VoteSessionService voteSessionService;

	@Operation(summary = "투표 세션 생성", description = "투표 세션을 생성합니다.")
	@PostMapping
	public ApiResponse<?> create(@RequestBody VoteSessionRequest voteSessionRequest) {
		voteSessionService.create(voteSessionRequest.id());
		return ResponseUtils.success();
	}

	@Operation(summary = "자신이 투표한 투표 세션 리스트 조회(진행중)", description = "현재 진행중인 자신이 투표한 투표 세션 리스트를 반환합니다.")
	@GetMapping("/my/ongoing")
	public ApiResponse<?> findByOngoingAndMy(@AuthenticationPrincipal PrincipalDetail principalDetail) {
		return ResponseUtils.success(voteSessionService.findByOngoingAndMy(principalDetail.getId()));
	}

	@Operation(summary = "자신이 투표한 투표 세션 리스트 조회(종료된)", description = "이미 종료된 자신이 투표한 투표 세션 리스트를 반환합니다.")
	@GetMapping("/my/end")
	public ApiResponse<?> findByEndAndMy(@AuthenticationPrincipal PrincipalDetail principalDetail) {
		return ResponseUtils.success(voteSessionService.findByEndAndMy(principalDetail.getId()));
	}

	@Operation(summary = "진행중인 투표 세션 조회", description = "현재 진행중인 투표 세션을 반환합니다.")
	@GetMapping("/ongoing")
	public ApiResponse<?> ongoingAll(@AuthenticationPrincipal PrincipalDetail principalDetail) {
		return ResponseUtils.success(voteSessionService.findAllByOngoing(principalDetail.getId()));
	}

	@Operation(summary = "종료된 투표 세션 조회", description = "이미 종료된 투표 세션을 반환합니다.")
	@GetMapping("/end")
	public ApiResponse<?> endAll(@AuthenticationPrincipal PrincipalDetail principalDetail) {
		return ResponseUtils.success(voteSessionService.findAllByEnd(principalDetail.getId()));
	}

	@Operation(summary = "특정 방송사에 진행중인 투표 세션 조회", description = "특정 방송사의 현재 진행중인 투표 세션을 반환합니다.")
	@GetMapping("/ongoing/{broadcastId}")
	public ApiResponse<?> ongoing(@PathVariable("broadcastId") Long broadcastId,
		@AuthenticationPrincipal PrincipalDetail principalDetail) {
		return ResponseUtils.success(
			voteSessionService.findAllByBroadcastIdAndOngoing(principalDetail.getId(), broadcastId));
	}

	@Operation(summary = "특정 방송사에 종료된 투표 세션 조회", description = "특정 방송사의 이미 종료된 투표 세션을 반환합니다.")
	@GetMapping("/end/{broadcastId}")
	public ApiResponse<?> end(@PathVariable("broadcastId") Long broadcastId,
		@AuthenticationPrincipal PrincipalDetail principalDetail) {
		return ResponseUtils.success(
			voteSessionService.findAllByBroadcastIdAndEnd(principalDetail.getId(), broadcastId));
	}

	@Operation(summary = "진행중인 투표 세션 네트워크 주소 조회", description = "현재 진행중인 투표 세션의 네트워크 주소를 반환합니다.")
	@GetMapping("/contract-address")
	public ApiResponse<?> findContractAddress() {
		return ResponseUtils.success(voteSessionService.findContractAddress());
	}

	@Operation(summary = "인기있는 투표 리스트 조회", description = "인기있는 투표 세션 3개를 반환합니다.")
	@GetMapping("/popular")
	public ApiResponse<?> findAllByPopular(@AuthenticationPrincipal PrincipalDetail principalDetail) {
		return ResponseUtils.success(voteSessionService.findAllByPopular(principalDetail.getId()));
	}

	@Operation(summary = "투표 세션 상세 정보 조회", description = "특정 방송사의 이미 종료된 투표 세션을 반환합니다.")
	@GetMapping("/{id}")
	public ApiResponse<?> findInfo(@PathVariable("id") String id) {
		return ResponseUtils.success(voteSessionService.findOne(id));
	}

	@Operation(summary = "투표 세션 검색 결과 조회", description = "검색 키워드가 포함된 현재 진행중인 투표 세션을 반환합니다.")
	@GetMapping("/search")
	public ApiResponse<?> findSearchList(@AuthenticationPrincipal PrincipalDetail principalDetail,
		@RequestParam String keyword) {
		return ResponseUtils.success(voteSessionService.findByTitle(principalDetail.getId(), keyword));
	}

	@Operation(summary = "투표 세션 결과 조회", description = "투표 세션의 결과를 반환합니다.")
	@GetMapping("/results/{id}")
	public ApiResponse<?> findResult(@PathVariable("id") String id,
		@AuthenticationPrincipal PrincipalDetail principalDetail) {
		return ResponseUtils.success(voteSessionService.findResult(principalDetail.getId(), id));
	}

	@Operation(summary = "내가 투표한 후보자 식별자 조회", description = "내가 투표한 후보자의 식별자를 조회합니다.")
	@GetMapping("/validate/{id}")
	public ApiResponse<?> findInfo(@PathVariable("id") String id,
		@AuthenticationPrincipal PrincipalDetail principalDetail) {
		Long votedCandidateId = voteSessionService.checkVotedCandidate(principalDetail.getId(), id);

		HashMap<String, String> map = new HashMap<>();
		map.put("votedCandidateId", null);
		return votedCandidateId == null ? ResponseUtils.success(map) :
			ResponseUtils.success("votedCandidateId", votedCandidateId);
	}
}
