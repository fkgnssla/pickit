package com.ssafy.pickit.domain.tempVoteSession.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class TempCandidateRequest {
	private String name;

	private MultipartFile multipartFile;

}
