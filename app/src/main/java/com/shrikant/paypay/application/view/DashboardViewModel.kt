package com.shrikant.paypay.application.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shrikant.paypay.application.data.model.base.result.Result
import com.shrikant.paypay.application.data.remote.dispatcher.DispatcherProvider
import com.shrikant.paypay.application.domain.usecase.AvailableCurrencies
import com.shrikant.paypay.application.domain.usecase.LatestExchangeRates
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode

class DashboardViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val availableCurrencies: AvailableCurrencies,
    private val latestExchangeRates: LatestExchangeRates
) : ViewModel() {
    private val _events = MutableStateFlow<DashboardEvents>(DashboardEvents.None)
    val events: StateFlow<DashboardEvents> = _events.asStateFlow()
    private val _availableCurrency = MutableStateFlow<List<String>>(emptyList())
    val availableCurrency: StateFlow<List<String>> = _availableCurrency.asStateFlow()
    private val _exchangeRates = MutableStateFlow<List<Pair<String, Double>>>(emptyList())
    val exchangeRates: StateFlow<List<Pair<String, Double>>> = _exchangeRates.asStateFlow()

    init {
        fetchCurrencies()
        fetchExchangeRates()
    }

    fun fetchCurrencies() {
        viewModelScope.launch(dispatcherProvider.io) {
            availableCurrencies().collect { result ->
                when (result) {
                    is Result.Success -> _availableCurrency.value = result.data.map { it.code }
                    is Result.Error -> {
                        _events.value = DashboardEvents.Error(result.exception.message.toString())
                    }
                }
            }
        }
    }

    fun fetchExchangeRates() {
        viewModelScope.launch(dispatcherProvider.io) {
            latestExchangeRates().collect { result ->
                when (result) {
                    is Result.Success -> _exchangeRates.value = result.data.rates.toList()
                    is Result.Error -> {
                        _events.value = DashboardEvents.Error(result.exception.message.toString())
                    }
                }
            }
        }
    }

    fun calculateAmount(
        amount: String,
        selectedCurrencyRate: Double,
        targetRate: Double
    ): BigDecimal {
        val inputAmount = BigDecimal(amount.ifEmpty { "0" })
        return if (selectedCurrencyRate > 0) {
            inputAmount.divide(BigDecimal(selectedCurrencyRate), 15, RoundingMode.HALF_UP)
                .multiply(BigDecimal(targetRate))
                .setScale(2, RoundingMode.HALF_UP)
        } else {
            BigDecimal.ZERO
        }
    }
}