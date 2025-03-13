package com.shrikant.paypay.application.domain.repo.di

import com.shrikant.paypay.application.view.di.core.scope.ActivityScope
import com.shrikant.paypay.application.data.remote.dispatcher.DispatcherProvider
import com.shrikant.paypay.application.data.remote.dispatcher.di.DispatcherProviderModule
import com.shrikant.paypay.application.data.remote.service.OpenExchangeService
import com.shrikant.paypay.application.data.repo.OpenExchangeRepo
import com.shrikant.paypay.application.domain.repo.OpenExchangeRepoImpl
import dagger.Module
import dagger.Provides

@Module(
    includes = [
        DispatcherProviderModule::class
    ]
)
object RepositoryModule {

    @Provides
    @ActivityScope
    fun provideOpenExchangeApiRepo(
        dispatcherProvider: DispatcherProvider,
        service: OpenExchangeService
    ): OpenExchangeRepo = OpenExchangeRepoImpl(dispatcherProvider, service)
}