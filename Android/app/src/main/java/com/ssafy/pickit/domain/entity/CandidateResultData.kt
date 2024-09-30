package com.ssafy.pickit.domain.entity

data class CandidateResultData(
    val candidateId: String,
    val candidateName: String,
    val voteCount: Long,
    val profileImage:String,
    val isVote:Boolean
)
