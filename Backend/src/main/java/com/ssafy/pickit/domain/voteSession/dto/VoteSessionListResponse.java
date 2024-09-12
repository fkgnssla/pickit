package com.ssafy.pickit.domain.voteSession.dto;

import java.time.LocalDateTime;

import com.ssafy.pickit.domain.voteSession.domain.VoteSession;

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

	public static VoteSessionListResponse of(VoteSession voteSession){
		return VoteSessionListResponse.builder()
			.id(voteSession.getId())
			.title(voteSession.getTitle())
			.imgUrl(voteSession.getImgUrl())
			.startDate(voteSession.getStartDate())
			.endDate(voteSession.getEndDate())
			.build();
	}
}
