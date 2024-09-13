package com.ssafy.pickit.domain.vote.domain;

import java.time.LocalDateTime;

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
}
