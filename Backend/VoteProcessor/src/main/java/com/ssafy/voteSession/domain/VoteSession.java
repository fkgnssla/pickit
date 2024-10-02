package com.ssafy.voteSession.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection = "vote_session")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class VoteSession {
	private String id;

	private String broadcastId;

	private String contractAddress;

	private String title;

	private String description;

	private String thumbnail;

	private Candidate winner;

	private List<Candidate> candidates;

	private LocalDateTime startDate;

	private LocalDateTime endDate;

	public void updateVoteCnt(Long candidateId) {
		for (Candidate candidate : candidates) {
			if (candidate.getNumber().equals(candidateId)) {
				candidate.updateVoteCnt();
				break;
			}
		}
	}
}
