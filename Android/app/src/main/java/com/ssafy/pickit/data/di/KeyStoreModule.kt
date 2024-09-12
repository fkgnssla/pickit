package com.ssafy.pickit.data.di


import android.content.Context
import com.ssafy.pickit.data.datasource.local.keystore.KeyStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KeyStoreModule {
    @Provides
    @Singleton
    fun provideKeyStoreHelper(@ApplicationContext context: Context) = KeyStoreManager(context)
}