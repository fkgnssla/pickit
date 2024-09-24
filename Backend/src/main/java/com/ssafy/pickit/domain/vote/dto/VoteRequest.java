package com.ssafy.pickit.domain.vote.dto;

public record VoteRequest(
	Long memberId,
	String voteSessionId,
	String candidateId,
	String transactionHash
) {
	public VoteRequest withMemberId(Long memberId) {
		return new VoteRequest(memberId, this.voteSessionId, this.candidateId, this.transactionHash);
	}
}

