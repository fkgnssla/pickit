package com.ssafy.pickit.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.pickit.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	public Member findBySocialId(String socialId);
}
