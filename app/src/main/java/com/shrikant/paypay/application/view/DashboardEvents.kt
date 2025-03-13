package com.shrikant.paypay.application.view

sealed class DashboardEvents {
    class AvailableCurrencies(val currencies: List<String>) : DashboardEvents()
    class ExchangeRates(val rates: Map<String, Double>) : DashboardEvents()
    object None : DashboardEvents()
    class Error(val message: String) : DashboardEvents()
}