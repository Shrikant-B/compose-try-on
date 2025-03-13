package com.shrikant.paypay.application.domain.usecase.impl

import com.shrikant.paypay.application.data.model.Currency
import com.shrikant.paypay.application.data.model.base.result.Result
import com.shrikant.paypay.application.data.repo.OpenExchangeRepo
import com.shrikant.paypay.application.domain.usecase.AvailableCurrencies
import kotlinx.coroutines.flow.Flow

internal class AvailableCurrenciesImpl(
    private val repo: OpenExchangeRepo
) : AvailableCurrencies {

    override operator fun invoke(): Flow<Result<List<Currency>>> {
        return repo.fetchAvailableCurrencies()
    }
}