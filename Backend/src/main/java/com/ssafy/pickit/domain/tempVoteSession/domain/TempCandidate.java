package com.ssafy.pickit.domain.tempVoteSession.domain;

import com.ssafy.pickit.domain.tempVoteSession.dto.TempCandidateRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TempCandidate {
	private String name;

	private String profileImg;

	private Long voteCnt;

	public static TempCandidate of(TempCandidateRequest request) {
		return TempCandidate.builder()
			.name(request.name())
			.profileImg(request.profileImg())
			.voteCnt(0L)
			.build();
	}
}
