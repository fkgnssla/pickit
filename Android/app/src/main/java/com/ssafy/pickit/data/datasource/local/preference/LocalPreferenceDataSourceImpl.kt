package com.ssafy.pickit.data.datasource.local.preference

import javax.inject.Inject

class LocalPreferenceDataSourceImpl @Inject constructor(
    private val localPreference: SharedPreference
) : LocalPreferenceDataSource {

    override fun setAccessToken(accessToken: String) {
        localPreference.setValue(ACCESS_TOKEN, accessToken)
    }

    override fun setPrivateKey(privateKey: String) {
        localPreference.setValue(PRIVATE_KEY, privateKey)
    }

    override fun setWalletAddress(walletAddress: String) {
        localPreference.setValue(WALLET_ADDRESS, walletAddress)
    }

    override fun setSocialId(socialId: String) {
        localPreference.setValue(SOCIAL_ID, socialId)
    }

    override fun setMemberId(memberId: String) {
        localPreference.setValue(MEMBER_ID, memberId)
    }

    override fun getAccessToken(): String? =
        localPreference.getValue(ACCESS_TOKEN)?.takeIf { it.isNotEmpty() }

    override fun getPrivateKey(): String? =
        localPreference.getValue(PRIVATE_KEY)?.takeIf { it.isNotEmpty() }

    override fun getWalletAddress(): String? =
        localPreference.getValue(WALLET_ADDRESS)?.takeIf { it.isNotEmpty() }

    override fun getSocialId(): String? =
        localPreference.getValue(SOCIAL_ID)?.takeIf { it.isNotEmpty() }

    override fun getMemberId(): String? =
        localPreference.getValue(MEMBER_ID)?.takeIf { it.isNotEmpty() }

    companion object {
        const val ACCESS_TOKEN = "ACCESS_TOKEN"
        const val PRIVATE_KEY = "PRIVATE_KEY"
        const val WALLET_ADDRESS = "WALLET_ADDRESS"
        const val SOCIAL_ID = "SOCIAL_ID"
        const val MEMBER_ID = "MEMBER_ID"
    }
}