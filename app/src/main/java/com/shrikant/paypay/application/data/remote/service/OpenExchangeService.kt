package com.shrikant.paypay.application.data.remote.service

import com.shrikant.paypay.application.data.model.Currency
import com.shrikant.paypay.application.data.model.base.ServerResponse

interface OpenExchangeService {
    suspend fun fetchAvailableCurrencies(): List<Currency>
    suspend fun fetchLatestExchangeRates(): ServerResponse
}