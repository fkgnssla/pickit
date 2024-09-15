package com.ssafy.pickit.domain.voteSession.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.ssafy.pickit.domain.voteSession.domain.Candidate;
import com.ssafy.pickit.domain.voteSession.domain.VoteSession;

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

	public static VoteSessionResponse of(VoteSession voteSession){
		return VoteSessionResponse.builder()
			.id(voteSession.getId())
			.title(voteSession.getTitle())
			.description(voteSession.getDescription())
			.imgUrl(voteSession.getImgUrl())
			.winner(voteSession.getWinner())
			.candidates(voteSession.getCandidates())
			.startDate(voteSession.getStartDate())
			.endDate(voteSession.getEndDate())
			.build();
	}
}
