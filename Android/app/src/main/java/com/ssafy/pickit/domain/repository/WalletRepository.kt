package com.ssafy.pickit.domain.repository

interface WalletRepository {
    suspend fun generateWallet()

    suspend fun insertWallet(
        privateKey: String,
        address: String
    )
}