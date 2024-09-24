package com.ssafy.pickit.domain.vote.dto;

public record VoteRequest(
	Long memberId,
	String voteSessionId,
	String candidateId,
	String transactionHash
) {
	public VoteRequest withMemberId(Long newMemberId) {
		return new VoteRequest(newMemberId, this.voteSessionId, this.candidateId, this.transactionHash);
	}
}

