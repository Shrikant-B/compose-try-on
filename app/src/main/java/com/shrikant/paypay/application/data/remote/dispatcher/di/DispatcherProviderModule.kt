package com.shrikant.paypay.application.data.remote.dispatcher.di

import com.shrikant.paypay.application.data.remote.dispatcher.DispatcherProvider
import com.shrikant.paypay.application.data.remote.dispatcher.DispatcherProviderImpl
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
object DispatcherProviderModule {

    @IODispatcher
    @Provides
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    fun provideDispatcherProvider(
        @IODispatcher ioDispatcher: CoroutineDispatcher,
        @MainDispatcher mainDispatcher: CoroutineDispatcher
    ): DispatcherProvider = DispatcherProviderImpl(ioDispatcher, mainDispatcher)
}