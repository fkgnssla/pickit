package com.ssafy.pickit.domain.entity

data class VoteListData(
    val id: String,
    val title: String,
    val thumbnail: String,
    val startDate: String,
    val endDate: String,
    val isVote: Boolean
)
