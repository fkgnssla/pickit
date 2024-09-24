package com.ssafy.pickit.data.di

import com.ssafy.pickit.domain.repository.AuthRepository
import com.ssafy.pickit.domain.repository.WalletRepository
import com.ssafy.pickit.domain.usecase.auth.LoginUseCase
import com.ssafy.pickit.domain.usecase.wallet.GenerateWalletUseCase
import com.ssafy.pickit.domain.usecase.wallet.InsertWalletUseCase
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
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase =
        LoginUseCase(authRepository)

    @Singleton
    @Provides
    fun provideGenerateWalletUseCase(walletRepository: WalletRepository): GenerateWalletUseCase =
        GenerateWalletUseCase(walletRepository)

    @Singleton
    @Provides
    fun provideInsertWalletUseCase(walletRepository: WalletRepository): InsertWalletUseCase =
        InsertWalletUseCase(walletRepository)
}