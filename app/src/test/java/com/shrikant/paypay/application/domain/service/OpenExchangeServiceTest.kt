package com.shrikant.paypay.application.domain.service

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.shrikant.paypay.application.data.model.ExchangeRate
import com.shrikant.paypay.application.data.model.base.error.ServerError
import com.shrikant.paypay.application.data.remote.OpenExchangeApis
import com.shrikant.paypay.application.data.remote.service.OpenExchangeService
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class OpenExchangeServiceTest {
    private val openExchangeApis: OpenExchangeApis = mock()
    private lateinit var subject: OpenExchangeService

    @Before
    fun setUp() {
        subject = OpenExchangeServiceImpl(openExchangeApis)
    }

    @Test
    fun `fetchAvailableCurrencies should return list of currencies`() = runTest {
        val mockResponse = mapOf(
            "USD" to "United States Dollar",
            "EUR" to "Euro"
        )
        whenever(openExchangeApis.fetchAvailableCurrencies()).thenReturn(mockResponse)

        val result = subject.fetchAvailableCurrencies()

        Assert.assertEquals(2, result.size)
        Assert.assertEquals("USD", result[0].code)
        Assert.assertEquals("United States Dollar", result[0].name)
        Assert.assertEquals("EUR", result[1].code)
        Assert.assertEquals("Euro", result[1].name)
    }

    @Test
    fun `fetchLatestExchangeRates should return exchange rate when app_id is valid`() = runTest {
        val mockExchangeRate = ExchangeRate(
            timestamp = 1234567890,
            base = "USD",
            rates = mapOf("EUR" to 0.85, "JPY" to 110.0)
        )
        whenever(openExchangeApis.fetchLatestRates()).thenReturn(mockExchangeRate)

        val result = subject.fetchLatestExchangeRates()

        Assert.assertTrue(result is ExchangeRate)
        Assert.assertEquals(mockExchangeRate.timestamp, (result as ExchangeRate).timestamp)
        Assert.assertEquals(mockExchangeRate.base, result.base)
        Assert.assertEquals(mockExchangeRate.rates, result.rates)
    }

    @Test
    fun `fetchLatestExchangeRates should return error when app_id is invalid`() = runTest {
        val mockError = ServerError(true, 401, "invalid_app_id", "Invalid app_id")
        whenever(openExchangeApis.fetchLatestRates()).thenReturn(mockError)

        val result = subject.fetchLatestExchangeRates()

        Assert.assertTrue(result is ServerError)
        Assert.assertEquals(mockError.error, (result as ServerError).error)
        Assert.assertEquals(mockError.message, result.message)
        Assert.assertEquals(mockError.description, result.description)
    }
}