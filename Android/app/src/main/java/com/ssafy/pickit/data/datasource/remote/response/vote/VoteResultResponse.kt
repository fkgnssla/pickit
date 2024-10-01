package com.ssafy.pickit.data.datasource.remote.response.vote

import com.google.gson.annotations.SerializedName

data class VoteResultResponse(
    @SerializedName("results")
    val results: List<CandidateResult>
)

data class CandidateResult(
    val candidateId: String,

    @SerializedName("name")
    val candidateName: String,

    @SerializedName("profile_img")
    val profileImage: String,

    @SerializedName("vote_cnt")
    val voteCount: Long,

    @SerializedName("is_vote")
    val isVote: Boolean
)
