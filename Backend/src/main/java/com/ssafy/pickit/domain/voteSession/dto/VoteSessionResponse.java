package com.ssafy.pickit.domain.voteSession.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.ssafy.pickit.domain.voteSession.domain.Candidate;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class VoteSessionResponse {
	private String id;

	private String title;

	private String description;

	private String imgUrl;

	private Candidate winner;

	private List<Candidate> candidates;

	private LocalDateTime startDate;

	private LocalDateTime endDate;
}
