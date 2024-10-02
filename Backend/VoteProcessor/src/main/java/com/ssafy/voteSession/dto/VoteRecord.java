package com.ssafy.voteSession.dto;

import com.ssafy.vote.dto.VoteRequest;

public record VoteRecord(
	Long memberId,
	Long candidateId,
	String transactionHash
) {

	public static VoteRecord of(VoteRequest vote){
		return new VoteRecord(vote.memberId(), vote.candidateId(),vote.transactionHash());
	}
}
