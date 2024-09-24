package com.ssafy.pickit.data.datasource.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class VoteResponse(
    val id: String,
    val imgUrl: String,
    val title: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,

) : Parcelable