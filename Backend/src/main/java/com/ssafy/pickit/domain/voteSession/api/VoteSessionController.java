package com.ssafy.pickit.domain.voteSession.api;

import org.springframework.http.ResponseEntity;
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

}
