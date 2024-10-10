package com.ssafy.pickit.domain.usecase.auth

import com.ssafy.pickit.domain.entity.RegisterItem
import com.ssafy.pickit.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(registerItem: RegisterItem): Boolean =
        authRepository.postRegister(registerItem)
}