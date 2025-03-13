package com.shrikant.paypay.application.domain.usecase

import com.shrikant.paypay.application.data.model.ExchangeRate
import com.shrikant.paypay.application.data.model.base.result.Result
import kotlinx.coroutines.flow.Flow

interface LatestExchangeRates {
    operator fun invoke(): Flow<Result<ExchangeRate>>
}