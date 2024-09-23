package com.ssafy.pickit.domain.voteSession.domain;

import java.util.UUID;

import com.ssafy.pickit.domain.tempVoteSession.domain.TempCandidate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class Candidate {
	private String id;

	private String name;

	private String profileImg;

	private Long voteCnt;

	public static Candidate of(TempCandidate request) {
		return Candidate.builder()
			.id(UUID.randomUUID().toString())
			.name(request.getName())
			.profileImg(request.getProfileImg())
			.voteCnt(0L)
			.build();
	}
}
