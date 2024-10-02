package com.ssafy.vote.dto;

public record VoteRequest(
	Long memberId,
	String voteSessionId,
	Long candidateId,
	String transactionHash
) {
}

