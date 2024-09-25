package com.ssafy.pickit.data.datasource.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class VoteSessionResponse(
    val id: String,
    val title: String,
    val description: String,
    val thumbnail: String,
    val candidates: List<Candidate>,
    @SerializedName("start_date") val startDate: LocalDateTime,
    @SerializedName("end_date") val endDate: LocalDateTime

) : Parcelable

@Parcelize
data class Candidate(
    val name: String,
    val profile_img: String,
    val voteCnt: Long
) : Parcelable