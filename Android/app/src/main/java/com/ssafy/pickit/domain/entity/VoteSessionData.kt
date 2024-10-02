package com.ssafy.pickit.domain.entity




data class VoteSessionData(
    val id: String,
    val contractAddress: String,
    val title: String,
    val description: String,
    val thumbnail: String,
    val candidates: List<CandidateData>,
    val startDate: String,
    val endDate: String
)

