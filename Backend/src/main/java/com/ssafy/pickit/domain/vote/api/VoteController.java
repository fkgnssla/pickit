package com.ssafy.pickit.domain.vote.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pickit.domain.vote.application.service.VoteService;
import com.ssafy.pickit.domain.vote.dto.VoteRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vote")
public class VoteController {
	private final VoteService voteService;

	@PostMapping
	public ResponseEntity<?> createVote(@RequestBody VoteRequest voteRequest) {
		voteService.submitVote(1L, voteRequest.getVoteSessionId(), voteRequest.getCandidateId(),
			voteRequest.getTransactionHash());
		return ResponseEntity.status(201).body("Vote Created");
	}
}
