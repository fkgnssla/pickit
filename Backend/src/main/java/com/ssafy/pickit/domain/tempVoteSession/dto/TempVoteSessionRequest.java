package com.ssafy.pickit.domain.tempVoteSession.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class TempVoteSessionRequest {
	private String broadcastId;

	private String title;

	private String description;

	private MultipartFile multipartFile;

	private List<TempCandidateRequest> candidates;

	private LocalDateTime startDate;

	private LocalDateTime endDate;
}
