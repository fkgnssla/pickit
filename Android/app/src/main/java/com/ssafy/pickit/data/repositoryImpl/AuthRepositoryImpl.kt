package com.ssafy.pickit.data.repositoryImpl

import android.util.Log
import com.ssafy.pickit.data.datasource.local.preference.LocalPreferenceDataSource
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
        val loginResponse = authApi.kakaoLogin(LoginRequest(logInItem.token)).data
        //TODO : status == fail 일 경우 예외 처리
        if(loginResponse.isExist && loginResponse.accessToken != null) {
            saveToken(loginResponse.accessToken)
        }else if(!loginResponse.isExist && loginResponse.socialId != null){
            saveSocialId(loginResponse.socialId)
        }

        return AuthMapper.mapperToLogInData(loginResponse)
    }

    private fun saveSocialId(socialId : String) {
        localPreferenceDataSource.setSocialId(socialId)
    }

    private fun saveToken(accessToken: String) {
        localPreferenceDataSource.setAccessToken(accessToken)
    }

    companion object {
        const val TAG = "AuthRepository"
    }

}