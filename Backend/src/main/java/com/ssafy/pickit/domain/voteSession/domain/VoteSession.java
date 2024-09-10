package com.ssafy.pickit.domain.voteSession.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

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

	private String broadcastId;

	private String contractAddress;

	private String title;

	private String description;

	private String imgUrl;

	private Candidate winner;

	private List<Candidate> candidates;

	private LocalDateTime startDate;

	private LocalDateTime endDate;

}
