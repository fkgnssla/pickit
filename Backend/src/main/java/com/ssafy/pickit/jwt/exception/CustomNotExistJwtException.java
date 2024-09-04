package com.ssafy.pickit.jwt.exception;

public class CustomNotExistJwtException extends RuntimeException {
	public CustomNotExistJwtException(String msg) {
		super(msg);
	}
}