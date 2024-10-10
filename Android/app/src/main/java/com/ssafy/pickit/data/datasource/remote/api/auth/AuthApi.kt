package com.ssafy.pickit.data.datasource.remote.api.auth

import com.ssafy.pickit.data.datasource.remote.request.auth.LoginRequest
import com.ssafy.pickit.data.datasource.remote.request.auth.RegisterRequest
import com.ssafy.pickit.data.datasource.remote.response.Auth.LoginResponse
import com.ssafy.pickit.data.datasource.remote.response.Auth.UserResponse
import com.ssafy.pickit.data.datasource.remote.response.ResponseWrapper
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/login")
    suspend fun kakaoLogin(
        @Body request: LoginRequest
    ): ResponseWrapper<LoginResponse>

    @POST("auth/sign-up")
    suspend fun register(
        @Body request: RegisterRequest
    ): ResponseWrapper<LoginResponse>

    @GET("members")
    suspend fun getUserData(): ResponseWrapper<UserResponse>
}