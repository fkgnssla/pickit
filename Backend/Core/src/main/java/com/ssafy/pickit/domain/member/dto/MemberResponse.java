package com.ssafy.pickit.domain.member.dto;

import com.ssafy.pickit.domain.member.domain.Member;

public record MemberResponse(
	String name,
	Integer age,
	String gender
) {
	public static MemberResponse from(Member member) {
		return new MemberResponse(
			member.getName(),
			member.getAge(),
			member.getGender().toString()
		);
	}
}
