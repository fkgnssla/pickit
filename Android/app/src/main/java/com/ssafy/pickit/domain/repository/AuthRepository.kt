package com.ssafy.pickit.domain.repository

import com.ssafy.pickit.domain.entity.LogInItem
import com.ssafy.pickit.domain.entity.LoginData
import com.ssafy.pickit.domain.entity.RegisterItem

interface AuthRepository {
    suspend fun postLogin(logInItem: LogInItem) : LoginData

    suspend fun postRegister(registerItem: RegisterItem) : Boolean
}