package com.ssafy.pickit.domain.broadcast.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pickit.domain.broadcast.application.service.BroadcastService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/broadcast")
public class BroadcastController {
	private final BroadcastService broadcastService;

	@GetMapping
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(broadcastService.findAll());
	}
}

