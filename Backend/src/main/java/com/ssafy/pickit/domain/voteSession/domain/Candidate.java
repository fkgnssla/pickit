package com.ssafy.pickit.domain.voteSession.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class Candidate {
	private String id;

	private String name;

	private String imgUrl;

	private Long voteCnt;
}
