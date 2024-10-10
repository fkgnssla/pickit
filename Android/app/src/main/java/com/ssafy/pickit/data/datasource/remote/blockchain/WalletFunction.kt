package com.ssafy.pickit.data.datasource.remote.blockchain

interface WalletFunction {
    fun generateWallet()
    fun insertUserWallet(privateKey: String)
    suspend fun vote(
        voteContractAddress: String,
        candidateId: Long
    ): VoteTransactionResponse
}