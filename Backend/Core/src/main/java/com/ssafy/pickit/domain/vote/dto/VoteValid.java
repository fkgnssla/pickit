package com.ssafy.pickit.domain.vote.dto;

import java.time.LocalDateTime;

public record VoteValid(
	Long memberId,
	String candidateId,
	String transactionHash,
	LocalDateTime createDate
) {
	public static VoteValid of(VoteRequest voteRequest) {
		return new VoteValid(
			voteRequest.memberId(), voteRequest.candidateId(), voteRequest.transactionHash(), LocalDateTime.now()
		);
	}
}
