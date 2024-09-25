package com.ssafy.pickit.data.datasource.remote.request.auth

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("age")
    val age: Int,
    @SerializedName("social_id")
    val socialId: String,
    @SerializedName("address")
    val address: String
)
