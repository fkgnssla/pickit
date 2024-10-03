package com.ssafy.pickit.domain.vote.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record VoteRequest(
	Long memberId,
	String voteSessionId,
	Long candidateId,
	String transactionHash
) {
	public VoteRequest withMemberId(Long memberId) {
		return new VoteRequest(memberId, this.voteSessionId, this.candidateId, this.transactionHash);
	}
}

