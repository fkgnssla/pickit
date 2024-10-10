package com.ssafy.pickit.domain.voteSession.dto;

import com.ssafy.pickit.domain.voteSession.domain.VoteSession;

public record PopularVoteSessionResponse(
	String voteSessionId,
	String thumbnail,
	Boolean isVote
) {
	public static PopularVoteSessionResponse from(VoteSession voteSession, Boolean isVote) {
		return new PopularVoteSessionResponse(voteSession.getId(), voteSession.getThumbnail(), isVote);
	}
}
