package com.shrikant.paypay.application.domain.service

import com.shrikant.paypay.application.data.model.Currency
import com.shrikant.paypay.application.data.model.base.ServerResponse
import com.shrikant.paypay.application.data.remote.OpenExchangeApis
import com.shrikant.paypay.application.data.remote.service.OpenExchangeService

internal class OpenExchangeServiceImpl(
    private val api: OpenExchangeApis
) : OpenExchangeService {

    override suspend fun fetchAvailableCurrencies(): List<Currency> {
        return api.fetchAvailableCurrencies().toCurrencyList()
    }

    override suspend fun fetchLatestExchangeRates(): ServerResponse {
        return api.fetchLatestRates()
    }

    private fun Map<String, String>.toCurrencyList(): List<Currency> {
        return this.map { (code, name) ->
            Currency(code = code, name = name)
        }
    }
}