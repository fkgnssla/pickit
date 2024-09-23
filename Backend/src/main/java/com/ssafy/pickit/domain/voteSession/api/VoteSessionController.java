package com.ssafy.pickit.domain.voteSession.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pickit.domain.voteSession.application.service.VoteSessionService;
import com.ssafy.pickit.domain.voteSession.dto.VoteSessionRequest;
import com.ssafy.pickit.global.response.ApiResponse;
import com.ssafy.pickit.global.response.ResponseUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "VoteSessionController", description = "투표 세션 API")
@RestController
@RequestMapping("/vote-session")
@RequiredArgsConstructor
public class VoteSessionController {
	private final VoteSessionService voteSessionService;

	@Operation(summary = "투표 세션 생성", description = "투표 세션을 생성합니다.")
	@PostMapping
	public ApiResponse<?> create(@RequestBody VoteSessionRequest voteSessionRequest) {
		voteSessionService.create(voteSessionRequest.id());
		return ResponseUtils.success();
	}

	@Operation(summary = "진행중인 투표 세션 조회", description = "현재 진행중인 투표 세션을 반환합니다.")
	@GetMapping("/ongoing")
	public ApiResponse<?> ongoingAll() {
		return ResponseUtils.success(voteSessionService.findAllByOngoing());
	}

	@Operation(summary = "종료된 투표 세션 조회", description = "이미 종료된 투표 세션을 반환합니다.")
	@GetMapping("/end")
	public ApiResponse<?> endAll() {
		return ResponseUtils.success(voteSessionService.findAllByEnd());
	}

	@Operation(summary = "특정 방송사에 진행중인 투표 세션 조회", description = "특정 방송사의 현재 진행중인 투표 세션을 반환합니다.")
	@GetMapping("/ongoing/{broadcastId}")
	public ApiResponse<?> ongoing(@PathVariable("broadcastId") String broadcastId) {
		return ResponseUtils.success(voteSessionService.findAllByBroadcastIdAndOngoing(broadcastId));
	}

	@Operation(summary = "특정 방송사에 종료된 투표 세션 조회", description = "특정 방송사의 이미 종료된 투표 세션을 반환합니다.")
	@GetMapping("/end/{broadcastId}")
	public ApiResponse<?> end(@PathVariable("broadcastId") String broadcastId) {
		return ResponseUtils.success(voteSessionService.findAllByBroadcastIdAndEnd(broadcastId));
	}

}
