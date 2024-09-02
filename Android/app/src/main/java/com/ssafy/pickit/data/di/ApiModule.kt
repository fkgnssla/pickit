package com.ssafy.pickit.data.di

import com.ssafy.pickit.data.datasource.remote.api.auth.AuthApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun providesAuthApi(retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)
}