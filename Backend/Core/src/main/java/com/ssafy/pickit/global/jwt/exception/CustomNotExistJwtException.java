package com.ssafy.pickit.global.jwt.exception;

public class CustomNotExistJwtException extends RuntimeException {
	public CustomNotExistJwtException(String msg) {
		super(msg);
	}
}