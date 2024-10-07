package com.ssafy.pickit.domain.usecase.auth


import com.ssafy.pickit.domain.entity.UserData
import com.ssafy.pickit.domain.repository.AuthRepository
import javax.inject.Inject

class UserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): UserData =
        authRepository.getUserData()
}