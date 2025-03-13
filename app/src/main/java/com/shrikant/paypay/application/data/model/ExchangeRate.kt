package com.shrikant.paypay.application.data.model

import com.shrikant.paypay.application.data.model.base.ServerResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ExchangeRate(
    @Json(name = "timestamp")
    val timestamp: Long,
    @Json(name = "base")
    val base: String,
    @Json(name = "rates")
    val rates: Map<String, Double> = emptyMap()
) : ServerResponse {
    fun getRateForCurrency(currency: String): Double {
        return rates[currency] ?: 0.0
    }
}