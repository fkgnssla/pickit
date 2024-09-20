package com.ssafy.pickit.data.datasource.remote.blockchain

interface WalletFunction {
    fun generateWallet()
    fun insertUserWallet(privateKey: String)
}