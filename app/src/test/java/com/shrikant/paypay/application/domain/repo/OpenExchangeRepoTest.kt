package com.shrikant.paypay.application.domain.repo

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.shrikant.paypay.application.data.model.Currency
import com.shrikant.paypay.application.data.model.ExchangeRate
import com.shrikant.paypay.application.data.model.base.ServerResponse
import com.shrikant.paypay.application.data.model.base.error.ServerError
import com.shrikant.paypay.application.data.model.base.result.Result
import com.shrikant.paypay.application.data.remote.dispatcher.DispatcherProvider
import com.shrikant.paypay.application.data.remote.service.OpenExchangeService
import com.shrikant.paypay.application.data.repo.OpenExchangeRepo
import com.shrikant.paypay.application.util.CoroutineDispatcherRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class OpenExchangeRepoTest {
    @get:Rule
    val coroutineDispatcherRule = CoroutineDispatcherRule()
    private val service: OpenExchangeService = mock()

    private lateinit var subject: OpenExchangeRepo

    @Before
    fun setUp() {
        val dispatcherProvider: DispatcherProvider = mock {
            on { io } doReturn coroutineDispatcherRule.testDispatcher
        }
        subject = OpenExchangeRepoImpl(dispatcherProvider, service)
    }

    @Test
    fun `fetchAvailableCurrencies returns list of currencies`() = runTest {
        val currencies = listOf(Currency("USD", "US Dollar"), Currency("EUR", "Euro"))
        whenever(service.fetchAvailableCurrencies()).thenReturn(currencies)

        val result = subject.fetchAvailableCurrencies().first()

        Assert.assertTrue(result is Result.Success)
        Assert.assertEquals(currencies, (result as Result.Success).data)
    }


    @Test
    fun `fetchAvailableCurrencies returns list of currencies as empty`() = runTest {
        whenever(service.fetchAvailableCurrencies()).thenReturn(emptyList<Currency>())

        val result = subject.fetchAvailableCurrencies().first()

        Assert.assertTrue(result is Result.Error)
        Assert.assertTrue((result as Result.Error).exception is IllegalStateException)
    }

    @Test
    fun `fetchAvailableCurrencies returns exception`() = runTest {
        val exception = RuntimeException("Network error")
        whenever(service.fetchAvailableCurrencies()).thenThrow(exception)

        val result = subject.fetchAvailableCurrencies().first()

        Assert.assertTrue(result is Result.Error)
        Assert.assertEquals(exception, (result as Result.Error).exception)
    }

    @Test
    fun `fetchLatestExchangeRates returns exchange rates`() = runTest {
        val exchangeRate = ExchangeRate(1234567890, "USD", mapOf("EUR" to 0.85))
        whenever(service.fetchLatestExchangeRates()).thenReturn(exchangeRate)

        val result = subject.fetchLatestExchangeRates().first()

        Assert.assertTrue(result is Result.Success)
        Assert.assertEquals(exchangeRate, (result as Result.Success).data)
    }

    @Test
    fun `fetchLatestExchangeRates returns server error`() = runTest {
        val error = ServerError(true, 401, "invalid_app_id", "Invalid app_id")
        whenever(service.fetchLatestExchangeRates()).thenReturn(error)

        val result = subject.fetchLatestExchangeRates().first()

        Assert.assertTrue(result is Result.Error)
        Assert.assertEquals("Invalid app_id", (result as Result.Error).exception.message)
    }

    @Test
    fun `fetchLatestExchangeRates returns server response`() = runTest {
        val response = object : ServerResponse {}
        whenever(service.fetchLatestExchangeRates()).thenReturn(response)

        val result = subject.fetchLatestExchangeRates().first()

        Assert.assertTrue(result is Result.Error)
        Assert.assertTrue((result as Result.Error).exception is IllegalStateException)
    }

    @Test
    fun `fetchLatestExchangeRates returns error on exception`() = runTest {
        val exception = RuntimeException("Network error")
        whenever(service.fetchLatestExchangeRates()).thenThrow(exception)

        val result = subject.fetchLatestExchangeRates().first()

        Assert.assertTrue(result is Result.Error)
        Assert.assertEquals(exception, (result as Result.Error).exception)
    }
}