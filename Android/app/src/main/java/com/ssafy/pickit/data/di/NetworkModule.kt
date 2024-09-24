package com.ssafy.pickit.data.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ssafy.pickit.BuildConfig
import com.ssafy.pickit.data.AuthInterceptor
import com.ssafy.pickit.data.datasource.local.preference.LocalPreferenceDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = BuildConfig.BASE_URL
    private const val CONTENT_TYPE = "application/json"

    @Provides
    @Singleton
    fun providesAuthInterceptor(localPreferenceDataSource: LocalPreferenceDataSource): Interceptor = AuthInterceptor(localPreferenceDataSource)

    @Provides
    @Singleton
    fun providesOkhttpClient(authInterceptor: Interceptor) = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .addInterceptor(authInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun providesRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

}