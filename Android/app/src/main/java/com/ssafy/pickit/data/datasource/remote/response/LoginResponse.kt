package com.ssafy.pickit.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("is_exist")
    val isExist: Boolean,
    @SerializedName("social_id")
    val socialId: String?,
    @SerializedName("access_token")
    val accessToken: String?,
    @SerializedName("refresh_token")
    val refreshToken: String?
)