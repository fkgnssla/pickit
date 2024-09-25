package com.ssafy.pickit.domain.usecase

import com.ssafy.pickit.domain.entity.LogInItem
import com.ssafy.pickit.domain.entity.LoginData
import com.ssafy.pickit.domain.repository.AuthRepository
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(logInItem: LogInItem): LoginData =
        authRepository.postLogin(logInItem)
}