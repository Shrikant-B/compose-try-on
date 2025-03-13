package com.shrikant.paypay.application.view.di.core.app.module

import com.shrikant.paypay.application.view.DashboardActivity
import com.shrikant.paypay.application.view.di.component.DashboardComponent
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(
    subcomponents = [DashboardComponent::class]
)
interface UiModule {

    @Binds
    @IntoMap
    @ClassKey(DashboardActivity::class)
    fun bindDashboardActivityInjectorFactory(
        factory: DashboardComponent.Factory
    ): AndroidInjector.Factory<*>
}