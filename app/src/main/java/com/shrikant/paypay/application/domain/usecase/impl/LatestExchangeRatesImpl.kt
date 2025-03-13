package com.shrikant.paypay.application.domain.usecase.impl

import com.shrikant.paypay.application.data.model.ExchangeRate
import com.shrikant.paypay.application.data.model.base.result.Result
import com.shrikant.paypay.application.data.repo.OpenExchangeRepo
import com.shrikant.paypay.application.domain.usecase.LatestExchangeRates
import kotlinx.coroutines.flow.Flow

internal class LatestExchangeRatesImpl(
    private val repo: OpenExchangeRepo
) : LatestExchangeRates {

    override operator fun invoke(): Flow<Result<ExchangeRate>> {
        return repo.fetchLatestExchangeRates()
    }
}