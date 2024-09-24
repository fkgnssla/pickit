package com.ssafy.pickit.domain.wallet.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.pickit.domain.wallet.domain.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
