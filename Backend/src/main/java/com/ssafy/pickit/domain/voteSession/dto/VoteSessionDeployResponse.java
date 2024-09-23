package com.ssafy.pickit.domain.voteSession.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record VoteSessionDeployResponse(
	@JsonProperty("status_code") Integer statusCode,
	String message,
	@JsonProperty("contract_address") String contractAddress,
	String output
) {
}

