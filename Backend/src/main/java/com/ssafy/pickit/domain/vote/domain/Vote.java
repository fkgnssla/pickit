package com.ssafy.pickit.domain.vote.domain;

import java.time.LocalDateTime;

import com.ssafy.pickit.domain.vote.dto.VoteRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class Vote {
	private Long memberId;
	private String candidateId;
	private String transactionHash;
	private LocalDateTime startDate;

	public static Vote of(VoteRequest voteRequest) {
		return Vote.builder()
			.memberId(voteRequest.getMemberId())
			.candidateId(voteRequest.getCandidateId())
			.transactionHash(voteRequest.getTransactionHash())
			.startDate(LocalDateTime.now()).build();
	}
}
