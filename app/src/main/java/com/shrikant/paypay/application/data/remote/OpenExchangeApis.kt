package com.shrikant.paypay.application.data.remote

import com.shrikant.paypay.application.BuildConfig
import com.shrikant.paypay.application.data.model.base.ServerResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenExchangeApis {

    @GET("api/currencies.json")
    suspend fun fetchAvailableCurrencies(
        @Query("app_id") appId: String = BuildConfig.API_KEY
    ): Map<String, String>

    @GET("api/latest.json")
    suspend fun fetchLatestRates(
        @Query("app_id") appId: String = BuildConfig.API_KEY
    ): ServerResponse
}