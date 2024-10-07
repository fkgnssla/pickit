package com.ssafy.pickit.domain.tempVoteSession.dto;

import java.time.LocalDateTime;
import java.util.List;

public record TempVoteSessionRequest(
	Long broadcastId,
	String title,
	String description,
	String thumbnail,
	List<TempCandidateRequest> candidates,
	LocalDateTime startDate,
	LocalDateTime endDate
) {
}

