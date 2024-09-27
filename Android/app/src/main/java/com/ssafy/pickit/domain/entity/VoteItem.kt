package com.ssafy.pickit.domain.entity

data class VoteItem(
    val voteSessionId: String,
    val candidateId: String,
    val transactionHash: String
)
