package com.ssafy.pickit.domain.tempVoteSession.dto;

public record TempCandidateRequest(
	Long number,
	String name,
	String profileImg
) {
}
