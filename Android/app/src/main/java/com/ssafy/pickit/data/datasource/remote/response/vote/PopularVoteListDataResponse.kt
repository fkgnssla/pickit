package com.ssafy.pickit.data.datasource.remote.response.vote

import com.google.gson.annotations.SerializedName

data class PopularVoteListDataResponse(
    @SerializedName("vote_session_id")
    val id: String,
    @SerializedName("thumbnail")
    val thumbnail: String
)