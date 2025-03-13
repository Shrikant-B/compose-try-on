package com.shrikant.paypay.application.domain.service.di

import com.shrikant.paypay.application.data.remote.OpenExchangeApis
import com.shrikant.paypay.application.data.remote.service.OpenExchangeService
import com.shrikant.paypay.application.domain.service.OpenExchangeServiceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object ServiceModule {

    @Provides
    @Singleton
    fun provideOpenExchangeService(
        api: OpenExchangeApis
    ): OpenExchangeService = OpenExchangeServiceImpl(api)
}