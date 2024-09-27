package com.ssafy.pickit.global.handler;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.pickit.domain.auth.exception.KakaoAPIException;
import com.ssafy.pickit.global.response.ApiResponse;
import com.ssafy.pickit.global.response.ResponseUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NoSuchElementException.class)
	public ApiResponse<?> handleIllegalArgumentException(NoSuchElementException ex) {
		log.error(ex.getMessage());
		return ResponseUtils.error(HttpStatus.NOT_FOUND, ex.getMessage());
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ApiResponse<?> handleIllegalArgumentException(IllegalArgumentException ex) {
		log.error(ex.getMessage());
		return ResponseUtils.error(HttpStatus.BAD_REQUEST, ex.getMessage());
	}

	@ExceptionHandler(KakaoAPIException.class)
	public ApiResponse<?> handleIllegalArgumentException(KakaoAPIException ex) {
		log.error(ex.getMessage());
		return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ApiResponse<?> handleGeneralException(Exception ex) {
		return ResponseUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
	}
}
