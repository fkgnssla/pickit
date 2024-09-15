package com.ssafy.pickit.domain.vote.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class VoteRequest {
	private Long memberId;
	private String voteSessionId;
	private String candidateId;
	private String transactionHash;

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
}
