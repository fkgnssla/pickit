package com.ssafy.pickit.data.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ssafy.pickit.BuildConfig
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
    fun providesAuthInterceptor(): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header(
                    "Authorization",
                    "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE3MjE1NzI2NDYsImV4cCI6MTczNzEyNDY0NiwidXNlcklkIjoxfQ.oLVNqOHnhsYgCtXJ5HfpTdSqsAqROvTB_NNKq5wgwqUL7ncHrUBO11tEEW89sYOs7gRCy-1Kf-XEtySm65e4BQ"
                )
                .header("Content-Type", CONTENT_TYPE)
            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

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