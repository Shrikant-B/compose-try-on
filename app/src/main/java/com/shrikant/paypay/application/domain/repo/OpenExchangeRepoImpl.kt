package com.shrikant.paypay.application.domain.repo

import com.shrikant.paypay.application.data.model.Currency
import com.shrikant.paypay.application.data.model.ExchangeRate
import com.shrikant.paypay.application.data.model.base.error.ServerError
import com.shrikant.paypay.application.data.model.base.result.Result
import com.shrikant.paypay.application.data.remote.dispatcher.DispatcherProvider
import com.shrikant.paypay.application.data.remote.service.OpenExchangeService
import com.shrikant.paypay.application.data.repo.OpenExchangeRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

internal class OpenExchangeRepoImpl(
    private val dispatcherProvider: DispatcherProvider,
    private val service: OpenExchangeService
) : OpenExchangeRepo {

    override fun fetchAvailableCurrencies(): Flow<Result<List<Currency>>> {
        return flow {
            emit(service.fetchAvailableCurrencies())
        }.map { response ->
            when {
                response.isEmpty().not() -> Result.Success(response)
                else -> Result.Error(IllegalStateException("Empty Response"))
            }
        }.catch {
            emit(Result.Error(it))
        }.flowOn(dispatcherProvider.io)
    }

    override fun fetchLatestExchangeRates(): Flow<Result<ExchangeRate>> {
        return flow {
            emit(service.fetchLatestExchangeRates())
        }.map { response ->
            when (response) {
                is ExchangeRate -> Result.Success(response)
                is ServerError -> Result.Error(Exception(response.description))
                else -> Result.Error(IllegalStateException("Empty Response"))
            }
        }.catch {
            emit(Result.Error(it))
        }.flowOn(dispatcherProvider.io)
    }
}