package com.ssafy.voteSession.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Candidate {
	private String id;

	private Long number;

	private String name;

	private String profileImg;

	private Long voteCnt;

	public void updateVoteCnt() {
		this.voteCnt++;
	}
}
