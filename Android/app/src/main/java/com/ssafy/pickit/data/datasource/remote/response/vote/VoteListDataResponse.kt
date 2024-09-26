package com.ssafy.pickit.data.datasource.remote.response.vote

import com.google.gson.annotations.SerializedName

data class VoteListDataResponse(

    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("start_date")
    val startDate: String,
    @SerializedName("end_date")
    val endDate: String,
    @SerializedName("is_vote")
    val isVote: Boolean
)