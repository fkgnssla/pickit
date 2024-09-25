package com.ssafy.pickit.data.datasource.remote.response

data class ApiResponse<T>(
    val status: String,
    val message: String?,
    val data: T?
)