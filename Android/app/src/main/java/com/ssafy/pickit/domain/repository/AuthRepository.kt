package com.ssafy.pickit.domain.repository

import com.ssafy.pickit.domain.entity.LogInItem
import com.ssafy.pickit.domain.entity.LoginData

interface AuthRepository {
    suspend fun postLogin(logInItem: LogInItem) : LoginData
}