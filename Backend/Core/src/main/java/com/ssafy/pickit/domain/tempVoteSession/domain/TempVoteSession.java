package com.ssafy.pickit.domain.tempVoteSession.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.ssafy.pickit.domain.tempVoteSession.dto.TempVoteSessionRequest;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection = "temp_vote_session")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TempVoteSession {
	@Id
	private String id;

	private Long broadcastId;

	private String title;

	private String description;

	private String thumbnail;

	private List<TempCandidate> candidates;

	private LocalDateTime startDate;

	private LocalDateTime endDate;

	public static TempVoteSession of(TempVoteSessionRequest voteSessionRequest,
		String thumbnail, List<TempCandidate> candidates) {
		return TempVoteSession.builder()
			.broadcastId(voteSessionRequest.broadcastId())
			.title(voteSessionRequest.title())
			.description(voteSessionRequest.description())
			.thumbnail(thumbnail)
			.candidates(candidates)
			.startDate(voteSessionRequest.startDate())
			.endDate(voteSessionRequest.endDate())
			.build();
	}

}
