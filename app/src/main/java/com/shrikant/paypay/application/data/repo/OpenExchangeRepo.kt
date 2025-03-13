package com.shrikant.paypay.application.data.repo

import com.shrikant.paypay.application.data.model.Currency
import com.shrikant.paypay.application.data.model.ExchangeRate
import com.shrikant.paypay.application.data.model.base.result.Result
import kotlinx.coroutines.flow.Flow

interface OpenExchangeRepo {
    fun fetchAvailableCurrencies(): Flow<Result<List<Currency>>>
    fun fetchLatestExchangeRates(): Flow<Result<ExchangeRate>>
}