package com.ssafy.pickit.domain.voteSession.dto;

import java.time.LocalDateTime;

import com.ssafy.pickit.domain.voteSession.domain.VoteSession;

public record VoteSessionListResponse(
	String id,
	String title,
	String thumbnail,
	LocalDateTime startDate,
	LocalDateTime endDate,
	Boolean isVote
) {
	public static VoteSessionListResponse from(VoteSession voteSession, Boolean isVote) {
		return new VoteSessionListResponse(
			voteSession.getId(),
			voteSession.getTitle(),
			voteSession.getThumbnail(),
			voteSession.getStartDate(),
			voteSession.getEndDate(),
			isVote
		);
	}
}

