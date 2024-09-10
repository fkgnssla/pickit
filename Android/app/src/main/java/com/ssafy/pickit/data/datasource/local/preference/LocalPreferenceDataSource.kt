package com.ssafy.pickit.data.datasource.local.preference

interface LocalPreferenceDataSource {
    fun setAccessToken(accessToken: String)

    fun getAccessToken(): String?
}