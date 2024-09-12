package com.ssafy.pickit.data.datasource.local.preference

interface LocalPreferenceDataSource {
    fun setAccessToken(accessToken: String)

    fun getAccessToken(): String?

    fun setPrivateKey(privateKey: String)

    fun getPrivateKey(): String?

    fun setWalletAddress(walletAddress: String)

    fun getWalletAddress(): String?

}