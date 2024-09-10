package com.ssafy.pickit.domain.member.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.pickit.domain.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	public Member findBySocialId(String socialId);
}
