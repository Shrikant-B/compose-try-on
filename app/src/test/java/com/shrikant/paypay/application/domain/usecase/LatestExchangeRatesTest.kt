package com.shrikant.paypay.application.domain.usecase

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.shrikant.paypay.application.data.model.ExchangeRate
import com.shrikant.paypay.application.data.model.base.result.Result
import com.shrikant.paypay.application.data.repo.OpenExchangeRepo
import com.shrikant.paypay.application.domain.usecase.impl.LatestExchangeRatesImpl
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LatestExchangeRatesTest {
    private val openExchangeRepo: OpenExchangeRepo = mock()
    private lateinit var subject: LatestExchangeRates

    @Before
    fun setUp() {
        subject = LatestExchangeRatesImpl(openExchangeRepo)
    }

    @Test
    fun `execute returns success with exchange rates`() = runTest {
        val exchangeRate = ExchangeRate(1234567890, "USD", mapOf("EUR" to 0.85))
        whenever(openExchangeRepo.fetchLatestExchangeRates()).thenReturn(
            flowOf(Result.Success(exchangeRate))
        )

        val result = subject.invoke().first()

        Assert.assertTrue(result is Result.Success)
        Assert.assertEquals(exchangeRate, (result as Result.Success).data)
    }

    @Test
    fun `execute returns error when repository fails`() = runTest {
        val error = RuntimeException("Network error")
        whenever(openExchangeRepo.fetchLatestExchangeRates()).thenReturn(
            flowOf(Result.Error(error))
        )

        val result = subject.invoke().first()

        Assert.assertTrue(result is Result.Error)
        Assert.assertEquals(error, (result as Result.Error).exception)
    }
}