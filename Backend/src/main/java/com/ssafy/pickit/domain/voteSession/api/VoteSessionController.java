package com.ssafy.pickit.domain.voteSession.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pickit.domain.voteSession.application.service.VoteSessionService;
import com.ssafy.pickit.domain.voteSession.dto.VoteSessionRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/vote-session")
@RequiredArgsConstructor
public class VoteSessionController {
	private final VoteSessionService voteSessionService;

	@PostMapping
	public ResponseEntity<?> create(@RequestBody VoteSessionRequest voteSessionRequest) {
		return ResponseEntity.ok(
			voteSessionService.create(voteSessionRequest.getId(), voteSessionRequest.getContractAddress()));
	}

	// 진행중인 투표 리스트 조회
	@GetMapping("/ongoing")
	public ResponseEntity<?> ongoingAll() {
		return ResponseEntity.ok(voteSessionService.findAllByOngoing());
	}

	// 이미 종료된 투표 리스트 조회
	@GetMapping("/end")
	public ResponseEntity<?> endAll() {
		return ResponseEntity.ok(voteSessionService.findAllByEnd());
	}

	// 방송사별 진행중인 투표 리스트 조회
	@GetMapping("/ongoing/{broadcastId}")
	public ResponseEntity<?> ongoing(@PathVariable("broadcastId") String broadcastId) {
		return ResponseEntity.ok(voteSessionService.findAllByBroadcastIdAndOngoing(broadcastId));
	}

	// 방송사별 이미 종료된 투표 리스트 조회
	@GetMapping("/end/{broadcastId}")
	public ResponseEntity<?> end(@PathVariable("broadcastId") String broadcastId) {
		return ResponseEntity.ok(voteSessionService.findAllByBroadcastIdAndEnd(broadcastId));
	}

}
