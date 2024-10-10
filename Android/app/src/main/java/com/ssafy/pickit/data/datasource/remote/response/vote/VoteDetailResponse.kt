package com.ssafy.pickit.data.datasource.remote.response.vote

import com.google.gson.annotations.SerializedName

data class VoteSessionResponse(
    val id: String,
    @SerializedName("contract_address")
    val contractAddress: String,
    val title: String,
    val description: String,
    val thumbnail: String,
    val candidates: List<CandidateResponse>,
    @SerializedName("start_date")
    val startDate: String,
    @SerializedName("end_date")
    val endDate: String

)


data class CandidateResponse(
    var id :String,
    val number: Long,
    val name: String,
    @SerializedName("profile_img") val profileImg: String,
    val voteCnt: Long
)