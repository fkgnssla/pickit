package com.ssafy.pickit.domain.broadcast.application.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.pickit.domain.broadcast.application.repository.BroadcastRepository;
import com.ssafy.pickit.domain.broadcast.domain.Broadcast;
import com.ssafy.pickit.domain.broadcast.dto.BroadcastResponse;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BroadcastService {
	private final BroadcastRepository broadcastRepository;

	public Broadcast findById(String id) {
		return broadcastRepository.findById(id).orElseThrow(() -> new NoSuchElementException("broadcast Not Found"));
	}

	public List<BroadcastResponse> findAll() {
		List<Broadcast> broadcasts = broadcastRepository.findAll();
		return mapToBroadcastResponse(broadcasts);
	}

	private static List<BroadcastResponse> mapToBroadcastResponse(List<Broadcast> broadcasts) {
		return broadcasts.stream()
			.map(broadcast -> BroadcastResponse.from(broadcast)).toList();
	}
}
