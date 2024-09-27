package com.ssafy.pickit.data.di

import android.content.Context
import com.ssafy.pickit.data.datasource.local.preference.SharedPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {
    @Provides
    @Singleton
    fun providesSharedPreference(@ApplicationContext context: Context) = SharedPreference(context)
}