package com.ssafy.pickit.domain.tempVoteSession.domain;

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
}
