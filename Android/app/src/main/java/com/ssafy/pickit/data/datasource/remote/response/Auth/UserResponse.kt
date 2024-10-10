package com.ssafy.pickit.data.datasource.remote.response.Auth

data class UserResponse(
    val id : Long,
    val name: String,
    val birthday: String,
    val gender: String
)