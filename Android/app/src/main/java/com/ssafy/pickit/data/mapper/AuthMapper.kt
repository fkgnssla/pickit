package com.ssafy.pickit.data.mapper

import com.ssafy.pickit.data.datasource.remote.response.Auth.LoginResponse
import com.ssafy.pickit.domain.entity.LoginData

object AuthMapper {
    fun mapperToLogInData(loginResponse: LoginResponse) : LoginData {
        return LoginData(
            loginResponse.isExist
        )
    }
}