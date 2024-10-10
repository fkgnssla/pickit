package com.ssafy.pickit.domain.repository

import com.ssafy.pickit.data.datasource.remote.response.Auth.UserResponse
import com.ssafy.pickit.domain.entity.LogInItem
import com.ssafy.pickit.domain.entity.LoginData
import com.ssafy.pickit.domain.entity.RegisterItem
import com.ssafy.pickit.domain.entity.UserData

interface AuthRepository {
    suspend fun postLogin(logInItem: LogInItem) : LoginData

    suspend fun postRegister(registerItem: RegisterItem) : Boolean

    suspend fun getUserData(): UserData
}