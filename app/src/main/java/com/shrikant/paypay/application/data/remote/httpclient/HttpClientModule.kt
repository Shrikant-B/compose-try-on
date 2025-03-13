package com.shrikant.paypay.application.data.remote.httpclient

import com.shrikant.paypay.application.data.remote.OpenExchangeApis
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://openexchangerates.org/"

@Module
object HttpClientModule {

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi): Retrofit = getRetrofitInstance(moshi, okHttpClient())

    @Provides
    @Singleton
    fun provideOpenExchangeApis(
        retrofit: Retrofit
    ): OpenExchangeApis = retrofit.create(OpenExchangeApis::class.java)

    private fun okHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply { level = Level.BODY })
        .build()

    private fun getRetrofitInstance(
        moshi: Moshi,
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(okHttpClient)
        .build()
}