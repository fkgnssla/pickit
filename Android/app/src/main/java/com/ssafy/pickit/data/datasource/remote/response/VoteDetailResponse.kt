package com.ssafy.pickit.data.datasource.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class VoteSessionResponse(
    val id: String,
    val title: String,
    val description: String,
    val imgUrl: String,
    val candidates: List<Candidate>,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,

) : Parcelable

@Parcelize
data class Candidate(
    val name: String,
    val imgUrl: String,
    val voteCnt: Long
) : Parcelable