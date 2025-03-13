package com.shrikant.paypay.application.data.remote.dispatcher

import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DispatcherProviderTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var subject: DispatcherProvider

    @Before
    fun setup() {
        subject = DispatcherProviderImpl(testDispatcher, testDispatcher)
    }

    @Test
    fun `verify dispatcher provider returns correct dispatchers`() {
        Assert.assertEquals(testDispatcher, subject.io)
        Assert.assertEquals(testDispatcher, subject.main)
    }
}