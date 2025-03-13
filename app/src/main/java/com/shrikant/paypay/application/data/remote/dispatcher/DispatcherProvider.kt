package com.shrikant.paypay.application.data.remote.dispatcher

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
}

internal class DispatcherProviderImpl(
    override val io: CoroutineDispatcher,
    override val main: CoroutineDispatcher
) : DispatcherProvider