package com.ssafy.pickit.global.error;

import lombok.Getter;

@Getter
public class LoggableException extends RuntimeException {

	public LoggableException(String message) {
		super(message);
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}

}
