package com.shrikant.paypay.application.view.di.component

import androidx.lifecycle.ViewModel
import com.shrikant.paypay.application.data.remote.dispatcher.DispatcherProvider
import com.shrikant.paypay.application.data.remote.dispatcher.di.DispatcherProviderModule
import com.shrikant.paypay.application.domain.usecase.AvailableCurrencies
import com.shrikant.paypay.application.domain.usecase.LatestExchangeRates
import com.shrikant.paypay.application.domain.usecase.di.UsecaseModule
import com.shrikant.paypay.application.view.DashboardActivity
import com.shrikant.paypay.application.view.DashboardViewModel
import com.shrikant.paypay.application.view.di.core.ViewModelFactoryModule
import com.shrikant.paypay.application.view.di.core.ViewModelKey
import com.shrikant.paypay.application.view.di.core.scope.ActivityScope
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

@ActivityScope
@Subcomponent(
    modules = [
        ViewModelFactoryModule::class,
        DispatcherProviderModule::class,
        UsecaseModule::class,
        DashboardModule::class
    ]
)
interface DashboardComponent : AndroidInjector<DashboardActivity> {

    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<DashboardActivity>
}

@Module
object DashboardModule {

    @ActivityScope
    @Provides
    @IntoMap
    @ViewModelKey(DashboardViewModel::class)
    fun provideDashboardViewModel(
        dispatcherProvider: DispatcherProvider,
        availableCurrencies: AvailableCurrencies,
        latestExchangeRates: LatestExchangeRates
    ): ViewModel = DashboardViewModel(
        dispatcherProvider,
        availableCurrencies,
        latestExchangeRates
    )
}