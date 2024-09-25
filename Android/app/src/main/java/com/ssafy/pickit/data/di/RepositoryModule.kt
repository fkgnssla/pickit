package com.ssafy.pickit.data.di

import com.ssafy.pickit.data.datasource.local.preference.LocalPreferenceDataSource
import com.ssafy.pickit.data.datasource.remote.api.auth.AuthApi
import com.ssafy.pickit.data.datasource.remote.blockchain.WalletFunction
import com.ssafy.pickit.data.repositoryImpl.AuthRepositoryImpl
import com.ssafy.pickit.data.repositoryImpl.WalletRepositoryImpl
import com.ssafy.pickit.domain.repository.AuthRepository
import com.ssafy.pickit.domain.repository.WalletRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun providesAuthRepository(
        localPreferenceDataSource: LocalPreferenceDataSource,
        authApi: AuthApi
    ): AuthRepository = AuthRepositoryImpl(localPreferenceDataSource, authApi)

    @Provides
    @Singleton
    fun providesWalletRepository(
        walletFunction: WalletFunction
    ): WalletRepository = WalletRepositoryImpl(walletFunction)
}