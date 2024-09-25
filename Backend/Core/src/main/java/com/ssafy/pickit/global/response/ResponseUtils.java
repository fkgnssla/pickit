package com.ssafy.pickit.global.response;

import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseUtils {

	/**
	 * 성공
	 */
	public static ApiResponse<Void> success() {
		return success(null);
	}

	public static <T> ApiResponse<Map<String, T>> success(String key, T object) {
		return success(Map.of(key, object));
	}

	public static <T> ApiResponse<T> success(T data) {
		return ApiResponse.<T>builder()
			.data(data)
			.status(ResponseStatus.SUCCESS)
			.build();
	}

	/**
	 * 에러
	 */

	public static ApiResponse<Void> error(HttpStatus httpStatus, String message) {
		return error(httpStatus, null, message);
	}

	public static <T> ApiResponse<T> error(HttpStatus httpStatus, T data, String message) {
		return ApiResponse.<T>builder()
			.data(data)
			.status(ResponseStatus.ERROR)
			.code(httpStatus.value() + "")
			.message(message)
			.build();
	}
}