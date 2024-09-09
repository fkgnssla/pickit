package com.ssafy.pickit.domain.tempVoteSession.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.ssafy.pickit.domain.tempVoteSession.domain.TempCandidate;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TempVoteSessionResponse {
	private String id;

	private String title;

	private String description;

	private List<TempCandidate> candidates;

	private LocalDateTime startDate;

	private LocalDateTime endDate;
}
