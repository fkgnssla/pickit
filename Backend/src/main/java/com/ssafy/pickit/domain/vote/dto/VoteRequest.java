package com.ssafy.pickit.domain.vote.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VoteRequest {
	private String voteSessionId;
	private String candidateId;
	private String transactionHash;
}
