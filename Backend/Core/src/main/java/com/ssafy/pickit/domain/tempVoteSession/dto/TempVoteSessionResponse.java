package com.ssafy.pickit.domain.tempVoteSession.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.ssafy.pickit.domain.broadcast.domain.Broadcast;
import com.ssafy.pickit.domain.tempVoteSession.domain.TempCandidate;
import com.ssafy.pickit.domain.tempVoteSession.domain.TempVoteSession;

public record TempVoteSessionResponse(
	String id,
	String broadcastName,
	String title,
	String description,
	String thumbnail,
	List<TempCandidate> candidates,
	LocalDateTime startDate,
	LocalDateTime endDate
) {
	public static TempVoteSessionResponse of(TempVoteSession tempVoteSession, Broadcast broadcast) {
		return new TempVoteSessionResponse(
			tempVoteSession.getId(),
			broadcast.getName(),
			tempVoteSession.getTitle(),
			tempVoteSession.getDescription(),
			tempVoteSession.getThumbnail(),
			tempVoteSession.getCandidates(),
			tempVoteSession.getStartDate(),
			tempVoteSession.getEndDate()
		);
	}
}
