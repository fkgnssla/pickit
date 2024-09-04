package com.ssafy.pickit.member.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class PrincipalDetail implements UserDetails {

	private Member member;
	private Collection<? extends GrantedAuthority> authorities;
	private boolean isNewMember;

	private Map<String, Object> attributes;

	public PrincipalDetail(Member member, Collection<? extends GrantedAuthority> authorities) {
		this.member = member;
		this.authorities = authorities;
	}

	public PrincipalDetail(Member member, Collection<? extends GrantedAuthority> authorities,
		Map<String, Object> attributes) {
		this.member = member;
		this.authorities = authorities;
		this.attributes = attributes;
	}

	public PrincipalDetail(Member member, boolean isNewMember, Collection<? extends GrantedAuthority> authorities,
		Map<String, Object> attributes) {
		this.member = member;
		this.isNewMember = isNewMember;
		this.authorities = authorities;
		this.attributes = attributes;
	}

	// info 에 들어가는 것들이 토큰에 들어가는 데이터
	public Map<String, Object> getMemberInfo() {
		Map<String, Object> info = new HashMap<>();
		info.put("id", member.getId());
		info.put("name", member.getName());
		info.put("role", member.getRole());
		return info;
	}

	public Long getId() {
		return member.getId();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return member.getId() + "";
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}