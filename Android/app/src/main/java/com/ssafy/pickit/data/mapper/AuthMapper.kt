package com.ssafy.pickit.data.mapper

import com.ssafy.pickit.data.datasource.remote.response.Auth.LoginResponse
import com.ssafy.pickit.data.datasource.remote.response.Auth.UserResponse
import com.ssafy.pickit.domain.entity.LoginData
import com.ssafy.pickit.domain.entity.UserData

object AuthMapper {
    fun mapperToLogInData(loginResponse: LoginResponse): LoginData {
        return LoginData(
            loginResponse.isExist
        )
    }

    fun mapperToUserData(userResponse: UserResponse): UserData {
        return UserData(
            name = userResponse.name,
            birthday = userResponse.birthday,
            gender = userResponse.gender
        )
    }
}