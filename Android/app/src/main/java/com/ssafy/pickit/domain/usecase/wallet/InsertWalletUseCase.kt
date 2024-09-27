package com.ssafy.pickit.domain.usecase.wallet

import com.ssafy.pickit.domain.repository.WalletRepository
import javax.inject.Inject

class InsertWalletUseCase @Inject constructor(
    private val walletRepository: WalletRepository
) {
    suspend operator fun invoke(privateKey: String) {
        walletRepository.insertWallet(privateKey)
    }
}