package com.ssafy.pickit.data.datasource.remote.response

data class ResponseWrapper<T>(
    val status: String,
    val data: T?,
    val code: String?,
    val message: String?
)

