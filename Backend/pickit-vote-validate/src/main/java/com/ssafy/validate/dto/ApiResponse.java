package com.ssafy.validate.dto;

import java.util.List;

public record ApiResponse(
	String status,
	List<String> data
) {
}
