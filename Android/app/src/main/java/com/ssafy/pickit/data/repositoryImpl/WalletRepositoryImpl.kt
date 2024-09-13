package com.ssafy.pickit.data.repositoryImpl

import com.ssafy.pickit.data.datasource.remote.blockchain.WalletFunction
import com.ssafy.pickit.domain.repository.WalletRepository
import javax.inject.Inject

class WalletRepositoryImpl @Inject constructor(
    private val walletFunction: WalletFunction
) : WalletRepository {
    override suspend fun generateWallet() {
        walletFunction.generateWallet()
    }

    override suspend fun insertWallet(
        privateKey: String,
        address: String
    ) {
        walletFunction.insertUserWallet(privateKey, address)
    }

    companion object {
        const val TAG = "WalletRepository"
    }

}