package com.ssafy.pickit.domain.member.dto;

import java.time.format.DateTimeFormatter;

import com.ssafy.pickit.domain.member.domain.Member;

public record MemberResponse(
	String name,
	String birthday,
	String gender
) {
	public static MemberResponse from(Member member) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
		return new MemberResponse(
			member.getName(),
			member.getBirthday().format(formatter),
			member.getGender().toString()
		);
	}
}
