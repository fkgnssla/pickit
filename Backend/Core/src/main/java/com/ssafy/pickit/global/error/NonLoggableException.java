package com.ssafy.pickit.global.error;

import lombok.Getter;

@Getter
public class NonLoggableException extends RuntimeException {

	public NonLoggableException(String message) {
		super(message);
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}

}
