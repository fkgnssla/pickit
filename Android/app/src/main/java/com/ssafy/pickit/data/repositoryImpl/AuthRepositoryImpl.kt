package com.ssafy.pickit.data.repositoryImpl

import com.ssafy.pickit.data.datasource.local.keystore.LocalKeyStoreManager
import com.ssafy.pickit.data.datasource.local.preference.LocalPreferenceDataSource
import com.ssafy.pickit.data.datasource.remote.api.auth.AuthApi
import com.ssafy.pickit.data.datasource.remote.request.auth.LoginRequest
import com.ssafy.pickit.data.datasource.remote.request.auth.RegisterRequest
import com.ssafy.pickit.data.mapper.AuthMapper
import com.ssafy.pickit.domain.entity.LogInItem
import com.ssafy.pickit.domain.entity.LoginData
import com.ssafy.pickit.domain.entity.RegisterItem
import com.ssafy.pickit.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val localPreferenceDataSource: LocalPreferenceDataSource,
    private val localKeyStoreManager: LocalKeyStoreManager,
    private val authApi: AuthApi
) : AuthRepository {
    override suspend fun postLogin(logInItem: LogInItem): LoginData {
        val loginResponse = authApi.kakaoLogin(LoginRequest(logInItem.token))

        //TODO : status == fail 일 경우(data non-null assertion 처리할 것) 예외 처리
        val data = loginResponse.data!!

        if (data.isExist && data.accessToken != null) {
            saveToken(data.accessToken)
        } else if (!data.isExist && data.socialId != null) {
            saveSocialId(data.socialId)
        }

        return AuthMapper.mapperToLogInData(data)
    }

    override suspend fun postRegister(registerItem: RegisterItem): Boolean {
        val socialId = localPreferenceDataSource.getSocialId() ?: return false
        val address = localKeyStoreManager.decryptData(
            localPreferenceDataSource.getWalletAddress() ?: return false
        )
        val registerResponse = authApi.register(
            RegisterRequest(
                registerItem.name,
                registerItem.age,
                registerItem.gender,
                socialId,
                address
            )
        )

        //TODO : status == fail 일 경우(data non-null assertion 처리할 것) 예외 처리
        val data = registerResponse.data!!
        if (data.accessToken != null) {
            saveToken(data.accessToken)
        } else return false

        return true
    }

    private fun saveSocialId(socialId: String) {
        localPreferenceDataSource.setSocialId(socialId)
    }

    private fun saveToken(accessToken: String) {
        localPreferenceDataSource.setAccessToken(accessToken)
    }

    companion object {
        const val TAG = "AuthRepository"
    }

}