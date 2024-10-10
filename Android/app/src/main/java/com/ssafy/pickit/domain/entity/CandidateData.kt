package com.ssafy.pickit.domain.entity

data class CandidateData(
    val id:String,
    val name: String,
    val profileImg: String,
    val voteCnt: Long,
    val candidateId : Long
)