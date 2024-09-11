package com.ssafy.pickit.domain.voteSession.dto;

import lombok.Data;

@Data
public class VoteSessionDeployResponse {
	private int status_code;
	private String message;
	private String contract_address;
	private String output;
}
