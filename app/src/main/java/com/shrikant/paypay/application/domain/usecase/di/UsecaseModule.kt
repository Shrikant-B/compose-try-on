package com.shrikant.paypay.application.domain.usecase.di

import com.shrikant.paypay.application.view.di.core.scope.ActivityScope
import com.shrikant.paypay.application.data.repo.OpenExchangeRepo
import com.shrikant.paypay.application.domain.repo.di.RepositoryModule
import com.shrikant.paypay.application.domain.usecase.AvailableCurrencies
import com.shrikant.paypay.application.domain.usecase.LatestExchangeRates
import com.shrikant.paypay.application.domain.usecase.impl.AvailableCurrenciesImpl
import com.shrikant.paypay.application.domain.usecase.impl.LatestExchangeRatesImpl
import dagger.Module
import dagger.Provides

@Module(includes = [RepositoryModule::class])
object UsecaseModule {

    @Provides
    @ActivityScope
    fun provideAvailableCurrencies(
        repo: OpenExchangeRepo
    ): AvailableCurrencies {
        return AvailableCurrenciesImpl(repo)
    }

    @Provides
    @ActivityScope
    fun provideLatestExchangeRates(
        repo: OpenExchangeRepo
    ): LatestExchangeRates {
        return LatestExchangeRatesImpl(repo)
    }
}