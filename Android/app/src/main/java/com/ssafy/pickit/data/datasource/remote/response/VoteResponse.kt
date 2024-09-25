package com.ssafy.pickit.data.datasource.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class VoteResponse(
    val id: String,
    @SerializedName("thumbnail") val thumbnail: String,
    val title: String,
    @SerializedName("start_date") val startDate: LocalDateTime,
    @SerializedName("end_date") val endDate: LocalDateTime

) : Parcelable