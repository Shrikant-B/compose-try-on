package com.shrikant.paypay.application.domain.usecase

import com.shrikant.paypay.application.data.model.Currency
import com.shrikant.paypay.application.data.model.base.result.Result
import kotlinx.coroutines.flow.Flow

interface AvailableCurrencies {
    operator fun invoke(): Flow<Result<List<Currency>>>
}