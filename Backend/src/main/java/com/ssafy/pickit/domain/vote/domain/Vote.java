package com.ssafy.pickit.domain.vote.domain;

import java.time.LocalDateTime;

import com.ssafy.pickit.domain.member.domain.Member;
import com.ssafy.pickit.domain.vote.dto.VoteRequest;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Vote {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	private String voteSessionId;

	private LocalDateTime createDate; //CRATE

	public static Vote of(VoteRequest voteRequest, Member member) {
		return Vote.builder()
			.member(member)
			.voteSessionId(voteRequest.voteSessionId())
			.createDate(LocalDateTime.now()).build();
	}
}
