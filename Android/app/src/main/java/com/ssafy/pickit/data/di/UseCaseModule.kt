package com.ssafy.pickit.data.di

import com.ssafy.pickit.domain.repository.AuthRepository
import com.ssafy.pickit.domain.usecase.AuthUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Singleton
    @Provides
    fun provideLoginUseCase(authRepository: AuthRepository): AuthUseCase =
        AuthUseCase(authRepository)
}