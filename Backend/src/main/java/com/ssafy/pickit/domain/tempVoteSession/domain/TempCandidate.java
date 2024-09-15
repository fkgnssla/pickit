package com.ssafy.pickit.domain.tempVoteSession.domain;

import com.ssafy.pickit.domain.tempVoteSession.dto.TempCandidateRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class TempCandidate {
	private String name;

	private String imgUrl;

	private Long voteCnt;

	public static TempCandidate of(TempCandidateRequest request){
		return TempCandidate.builder()
			.name(request.getName())
			.imgUrl("sample_url") // 상수화된 이미지 URL
			.voteCnt(0L)
			.build();
	}
}
