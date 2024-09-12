package com.ssafy.pickit.domain.tempVoteSession.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.ssafy.pickit.domain.broadcast.domain.Broadcast;
import com.ssafy.pickit.domain.tempVoteSession.domain.TempCandidate;
import com.ssafy.pickit.domain.tempVoteSession.domain.TempVoteSession;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TempVoteSessionResponse {
	private String id;

	private String broadcastName;

	private String title;

	private String description;

	private String imgUrl;

	private List<TempCandidate> candidates;

	private LocalDateTime startDate;

	private LocalDateTime endDate;

	public static TempVoteSessionResponse of(TempVoteSession tempVoteSession, Broadcast broadcast){
		return TempVoteSessionResponse.builder()
			.id(tempVoteSession.getId())
			.broadcastName(broadcast.getName())
			.title(tempVoteSession.getTitle())
			.description(tempVoteSession.getDescription())
			.candidates(tempVoteSession.getCandidates())
			.imgUrl(tempVoteSession.getImgUrl())
			.startDate(tempVoteSession.getStartDate())
			.endDate(tempVoteSession.getEndDate())
			.build();
	}
}
