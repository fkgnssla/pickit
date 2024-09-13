package com.ssafy.pickit.domain.vote.application.service;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.ssafy.pickit.domain.vote.domain.Vote;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VoteService {
	private final MongoTemplate mongoTemplate;

	public void submitVote(Long memberId, String voteSessionId, String candidateId, String transactionHash) {
		Vote vote = Vote.builder()
			.memberId(memberId)
			.candidateId(candidateId)
			.transactionHash(transactionHash)
			.startDate(LocalDateTime.now()).build();

		mongoTemplate.insert(vote, voteSessionId);
	}
}

