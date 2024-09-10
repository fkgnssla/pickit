package com.ssafy.pickit.domain.voteSession.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class VoteSessionListResponse {
	private String id;

	private String title;

	private String imgUrl;

	private LocalDateTime startDate;

	private LocalDateTime endDate;
}
