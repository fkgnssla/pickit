package com.ssafy.pickit.data.datasource.remote.blockchain

import org.web3j.protocol.core.methods.response.EthBlock.TransactionHash

sealed class TransactionState {
    object Success : TransactionState()
    object Failure : TransactionState()
}