package com.ssafy.pickit.data.datasource.local

interface LocalPreferenceDataSource {
    fun setAccessToken(accessToken: String)

    fun getAccessToken(): String?
}