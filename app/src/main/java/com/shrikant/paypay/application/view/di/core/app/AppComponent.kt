package com.shrikant.paypay.application.view.di.core.app

import android.app.Application
import com.shrikant.paypay.application.PaypayApp
import com.shrikant.paypay.application.data.remote.httpclient.HttpClientModule
import com.shrikant.paypay.application.data.remote.parser.di.MoshiModule
import com.shrikant.paypay.application.domain.service.di.ServiceModule
import com.shrikant.paypay.application.view.di.core.app.module.AppModule
import com.shrikant.paypay.application.view.di.core.app.module.UiModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        UiModule::class,
        ServiceModule::class,
        HttpClientModule::class,
        MoshiModule::class
    ]
)
interface AppComponent : AndroidInjector<PaypayApp> {

    @Component.Factory
    interface Builder {
        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }

    object Injector {
        private lateinit var appComponent: AppComponent

        @JvmStatic
        fun init(application: PaypayApp): AppComponent {
            appComponent = DaggerAppComponent.factory().create(application)
            return appComponent
        }

        @JvmStatic
        fun getAppComponent(): AppComponent = appComponent
    }
}