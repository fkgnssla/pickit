package com.ssafy.pickit.domain.voteSession.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.ssafy.pickit.domain.tempVoteSession.domain.TempVoteSession;

import jakarta.persistence.Id;
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
	@Id
	private String id;

	private Long broadcastId;

	private String contractAddress;

	private String title;

	private String description;

	private String thumbnail;

	private Candidate winner;

	private List<Candidate> candidates;

	private LocalDateTime startDate;

	private LocalDateTime endDate;

	public static VoteSession of(TempVoteSession tempVoteSession, String contractAddress,
		List<Candidate> candidates) {
		return VoteSession.builder()
			.broadcastId(tempVoteSession.getBroadcastId())
			.contractAddress(contractAddress)
			.title(tempVoteSession.getTitle())
			.description(tempVoteSession.getDescription())
			.thumbnail(tempVoteSession.getThumbnail())
			.winner(null)
			.candidates(candidates)
			.startDate(tempVoteSession.getStartDate())
			.endDate(tempVoteSession.getEndDate())
			.build();
	}

	public void updateVoteCnt(Long candidateId) {
		for (Candidate candidate : candidates) {
			if (candidate.getNumber() == candidateId) {
				candidate.updateVoteCnt();
				break;
			}
		}
	}
}
