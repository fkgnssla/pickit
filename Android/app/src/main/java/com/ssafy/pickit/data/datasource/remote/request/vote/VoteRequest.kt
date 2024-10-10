package com.ssafy.pickit.data.datasource.remote.request.vote

import com.google.gson.annotations.SerializedName

data class VoteRequest(
    @SerializedName("vote_session_id")
    val voteSessionId: String,
    @SerializedName("candidate_id")
    val candidateId: Long,
    @SerializedName("transaction_hash")
    val transactionHash: String
)
