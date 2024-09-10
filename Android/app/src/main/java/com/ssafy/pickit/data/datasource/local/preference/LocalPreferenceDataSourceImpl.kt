package com.ssafy.pickit.data.datasource.local.preference

import javax.inject.Inject

class LocalPreferenceDataSourceImpl @Inject constructor(
    private val localPreference: SharedPreference
) : LocalPreferenceDataSource {
    override fun getAccessToken(): String? =
        SharedPreference.getValue(ACCESS_TOKEN)?.takeIf { it.isNotEmpty() }

    override fun setAccessToken(accessToken: String) {
        SharedPreference.setValue(ACCESS_TOKEN, accessToken)
    }

    companion object {
        const val ACCESS_TOKEN = "ACCESS_TOKEN"
    }
}