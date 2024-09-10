package com.ssafy.pickit.domain.tempVoteSession.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

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

	private String broadcastId;

	private String contractAddress;

	private String title;

	private String description;

	private String imgUrl;

	private List<TempCandidate> candidates;

	private LocalDateTime startDate;

	private LocalDateTime endDate;

}
