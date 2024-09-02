package com.ssafy.pickit.data.repositoryImpl

import com.ssafy.pickit.data.datasource.local.LocalPreferenceDataSource
import com.ssafy.pickit.data.datasource.local.SharedPreference
import com.ssafy.pickit.data.datasource.remote.api.auth.AuthApi
import com.ssafy.pickit.data.datasource.remote.request.LoginRequest
import com.ssafy.pickit.data.mapper.AuthMapper
import com.ssafy.pickit.domain.entity.LogInItem
import com.ssafy.pickit.domain.entity.LoginData
import com.ssafy.pickit.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val localPreferenceDataSource: LocalPreferenceDataSource,
    private val authApi: AuthApi
) : AuthRepository {
    override suspend fun postLogin(logInItem: LogInItem): LoginData {
        val loginResponse = authApi.kakaoLogin(LoginRequest(logInItem.kakaoToken))
        saveToken(loginResponse.accessToken)

        return AuthMapper.mapperToLogInData(loginResponse)
    }

    private fun saveToken(accessToken: String) {
        localPreferenceDataSource.setAccessToken(accessToken);
    }

    companion object {
        const val TAG = "AuthRepository"
    }

}