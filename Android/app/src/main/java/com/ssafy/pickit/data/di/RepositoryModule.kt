package com.ssafy.pickit.data.di

import com.ssafy.pickit.data.datasource.local.LocalPreferenceDataSource

import com.ssafy.pickit.data.datasource.remote.api.auth.AuthApi
import com.ssafy.pickit.data.datasource.remote.api.vote.VoteApi

import com.ssafy.pickit.data.repositoryImpl.AuthRepositoryImpl
import com.ssafy.pickit.data.repositoryImpl.VoteRepositoryImpl

import com.ssafy.pickit.domain.repository.AuthRepository
import com.ssafy.pickit.domain.repository.VoteRepository
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
    ) : AuthRepository = AuthRepositoryImpl(localPreferenceDataSource,authApi)

    @Provides
    @Singleton
    fun providesVoteRepository(
        voteApi: VoteApi
    ): VoteRepository = VoteRepositoryImpl(voteApi)


}