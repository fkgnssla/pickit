package com.ssafy.pickit.domain.voteSession.dto;

import com.ssafy.pickit.domain.voteSession.domain.Candidate;

public record CandidateResponse(
	String name,
	String profileImg,
	Long voteCnt,
	Boolean isVote) {

	public static CandidateResponse of(Candidate candidate, Boolean isVote) {
		return new CandidateResponse(candidate.getName(), candidate.getProfileImg(), candidate.getVoteCnt(), isVote);
	}
}
