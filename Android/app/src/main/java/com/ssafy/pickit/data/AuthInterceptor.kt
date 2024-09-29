package com.ssafy.pickit.data

import com.ssafy.pickit.data.datasource.local.preference.LocalPreferenceDataSource
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val localPreferenceDataSource: LocalPreferenceDataSource
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val request = if (!isAuth(originalRequest))
            originalRequest.appendToken(localPreferenceDataSource.getAccessToken() ?: "")
        else
            originalRequest

        return chain.proceed(request)
    }


    private fun isAuth(originalRequest: Request) =
        originalRequest.url.encodedPath.contains("auth")

    private fun Request.appendToken(accessToken: String): Request =
        newBuilder()
            .addHeader(
                "Authorization",
                "Bearer $accessToken"
            )
            .build()
}