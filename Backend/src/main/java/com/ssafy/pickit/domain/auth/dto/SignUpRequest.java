package com.ssafy.pickit.domain.auth.dto;

import lombok.Data;

@Data
public class SignUpRequest {
	private String name;
	private int age;
	private String gender;
	private String socialId;
	private String address;
}
