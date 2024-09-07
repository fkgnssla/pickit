package com.ssafy.pickit.domain.wallet.application.service;

import org.springframework.stereotype.Service;

import com.ssafy.pickit.domain.wallet.application.repository.WalletRepository;
import com.ssafy.pickit.domain.wallet.domain.Wallet;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalletService {
	private final WalletRepository walletRepository;

	public Wallet create(String address, String privateKey) {
		Wallet newWallet = Wallet.builder()
			.address(address)
			.privateKey(privateKey).build();

		return walletRepository.save(newWallet);
	}
}
