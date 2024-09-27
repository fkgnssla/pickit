package com.ssafy.pickit.domain.voteSession.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.ssafy.pickit.domain.voteSession.domain.Candidate;
import com.ssafy.pickit.domain.voteSession.domain.VoteSession;

public record VoteSessionResponse(
	String id,
	String title,
	String description,
	String thumbnail,
	List<Candidate> candidates,
	LocalDateTime startDate,
	LocalDateTime endDate
) {
	public static VoteSessionResponse from(VoteSession voteSession) {
		return new VoteSessionResponse(
			voteSession.getId(),
			voteSession.getTitle(),
			voteSession.getDescription(),
			voteSession.getThumbnail(),
			voteSession.getCandidates(),
			voteSession.getStartDate(),
			voteSession.getEndDate()
		);
	}
}

