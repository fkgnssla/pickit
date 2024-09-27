package com.ssafy.pickit.data.di

import com.ssafy.pickit.data.datasource.local.keystore.KeyStoreManager
import com.ssafy.pickit.data.datasource.local.keystore.LocalKeyStoreManager
import com.ssafy.pickit.data.datasource.local.keystore.LocalKeyStoreManagerImpl
import com.ssafy.pickit.data.datasource.local.preference.LocalPreferenceDataSource
import com.ssafy.pickit.data.datasource.local.preference.LocalPreferenceDataSourceImpl
import com.ssafy.pickit.data.datasource.local.preference.SharedPreference
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
    fun providesLocalPreferences(preference: SharedPreference): LocalPreferenceDataSource =
        LocalPreferenceDataSourceImpl(preference)

    @Provides
    @Singleton
    fun provideLocalKeyStoreHelper(
        keyStoreManager: KeyStoreManager
    ): LocalKeyStoreManager {
        return LocalKeyStoreManagerImpl(keyStoreManager)
    }
}