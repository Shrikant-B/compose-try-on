package com.shrikant.paypay.application.domain.usecase

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.shrikant.paypay.application.data.model.Currency
import com.shrikant.paypay.application.data.model.base.result.Result
import com.shrikant.paypay.application.data.repo.OpenExchangeRepo
import com.shrikant.paypay.application.domain.usecase.impl.AvailableCurrenciesImpl
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AvailableCurrenciesTest {
    private val openExchangeRepo: OpenExchangeRepo = mock()
    private lateinit var subject: AvailableCurrencies

    @Before
    fun setUp() {
        subject = AvailableCurrenciesImpl(openExchangeRepo)
    }

    @Test
    fun `execute returns success with exchange rates`() = runTest {
        val currencies = listOf(Currency("USD", "US Dollar"), Currency("EUR", "Euro"))
        whenever(openExchangeRepo.fetchAvailableCurrencies()).thenReturn(
            flowOf(Result.Success(currencies))
        )

        val result = subject.invoke().first()

        Assert.assertTrue(result is Result.Success)
        Assert.assertEquals(currencies, (result as Result.Success).data)
    }

    @Test
    fun `execute returns error when repository fails`() = runTest {
        val error = RuntimeException("Network error")
        whenever(openExchangeRepo.fetchAvailableCurrencies()).thenReturn(
            flowOf(Result.Error(error))
        )

        val result = subject.invoke().first()

        Assert.assertTrue(result is Result.Error)
        Assert.assertEquals(error, (result as Result.Error).exception)
    }
}