package com.ssafy.pickit.data.datasource.remote.blockchain

data class VoteTransactionResponse(
    val status: TransactionState,
    val transactionHash: String? = null,
    val message: String? = null
)
