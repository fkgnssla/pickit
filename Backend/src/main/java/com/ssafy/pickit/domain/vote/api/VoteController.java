package com.ssafy.pickit.domain.vote.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pickit.domain.member.domain.PrincipalDetail;
import com.ssafy.pickit.domain.vote.application.service.VoteProducer;
import com.ssafy.pickit.domain.vote.dto.VoteRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vote")
public class VoteController {
	// private final VoteService voteService;
	private final VoteProducer voteProducer;

	@PostMapping
	public ResponseEntity<?> createVote(@RequestBody VoteRequest voteRequest,
		@AuthenticationPrincipal PrincipalDetail principalDetail) {
		voteProducer.submitVote(1L, voteRequest);
		return ResponseEntity.status(201).body("Vote Sended");
	}
}
