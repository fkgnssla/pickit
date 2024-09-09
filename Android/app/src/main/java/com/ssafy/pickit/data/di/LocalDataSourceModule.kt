package com.ssafy.pickit.data.di

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
    fun providesLocalPreferences(): LocalPreferenceDataSource =
        LocalPreferenceDataSourceImpl(SharedPreference)

    @Provides
    @Singleton
    fun provideKeyStoreHelper(@ApplicationContext context: Context): KeyStoreHelper {
        return KeyStoreHelper(context)
    }
}