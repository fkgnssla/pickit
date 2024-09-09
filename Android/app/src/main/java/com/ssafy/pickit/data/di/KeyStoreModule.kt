package com.ssafy.pickit.data.di


import com.ssafy.pickit.data.datasource.local.keystore.KeyStoreManager
import com.ssafy.pickit.data.datasource.local.keystore.LocalKeyStoreManager
import com.ssafy.pickit.data.datasource.local.keystore.LocalKeyStoreManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KeyStoreModule {

    @Provides
    @Singleton
    fun provideLocalKeyStoreHelper(
        keyStoreHelper: KeyStoreManager
    ): LocalKeyStoreManager {
        return LocalKeyStoreManagerImpl(keyStoreHelper)
    }
}