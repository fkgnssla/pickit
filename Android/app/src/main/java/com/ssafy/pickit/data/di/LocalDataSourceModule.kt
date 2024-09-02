package com.ssafy.pickit.data.di

import com.ssafy.pickit.data.datasource.local.LocalPreferenceDataSource
import com.ssafy.pickit.data.datasource.local.LocalPreferenceDataSourceImpl
import com.ssafy.pickit.data.datasource.local.SharedPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataSourceModule {
    @Provides
    @Singleton
    fun providesLocalPreferences(): LocalPreferenceDataSource =
        LocalPreferenceDataSourceImpl(SharedPreference)
}