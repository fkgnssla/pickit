package com.ssafy.pickit.data.di

import com.ssafy.pickit.data.datasource.local.keystore.LocalKeyStoreManager
import com.ssafy.pickit.data.datasource.local.preference.LocalPreferenceDataSource
import com.ssafy.pickit.data.datasource.remote.blockchain.WalletFunction
import com.ssafy.pickit.data.datasource.remote.blockchain.WalletFunctionImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BlockChainModule {

    @Provides
    @Singleton
    fun providesWalletFunction(
        localPreferenceDataSource: LocalPreferenceDataSource,
        localKeyStoreManager: LocalKeyStoreManager
    ): WalletFunction = WalletFunctionImpl(localPreferenceDataSource, localKeyStoreManager)
}