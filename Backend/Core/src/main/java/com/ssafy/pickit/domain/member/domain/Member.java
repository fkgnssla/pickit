package com.ssafy.pickit.domain.member.domain;

import java.time.LocalDate;

import com.ssafy.pickit.domain.auth.dto.SignUpRequest;
import com.ssafy.pickit.domain.wallet.domain.Wallet;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "wallet_id")
	private Wallet wallet;

	private String name;

	private LocalDate birthday;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	private String socialId;

	@Enumerated(EnumType.STRING)
	private Role role;

	public static Member of(SignUpRequest signUpRequest, Wallet newWallet, LocalDate birthday) {
		return Member.builder()
			.name(signUpRequest.name())
			.birthday(birthday)
			.gender(Gender.valueOf(signUpRequest.gender().toUpperCase()))
			.socialId(signUpRequest.socialId())
			.wallet(newWallet)
			.role(Role.MEMBER).build();
	}

}
