package com.ssafy.pickit.data.datasource.remote.api.auth

import com.ssafy.pickit.data.datasource.remote.request.LoginRequest
import com.ssafy.pickit.data.datasource.remote.response.LoginResponse
import com.ssafy.pickit.data.datasource.remote.response.ResponseWrapper
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/login")
    suspend fun kakaoLogin(
        @Body request: LoginRequest
    ): ResponseWrapper<LoginResponse>
}