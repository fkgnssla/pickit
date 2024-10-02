package com.ssafy.pickit.data.datasource.remote.response.vote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

data class VoteSessionResponse(
    val id: String,
    val title: String,
    val description: String,
    val thumbnail: String,
    val candidates: List<CandidateResponse>,
    @SerializedName("start_date") val startDate: String,
    @SerializedName("end_date") val endDate: String

)


data class CandidateResponse(
    val name: String,
    @SerializedName("profile_img") val profileImg: String,
    val voteCnt: Long
)