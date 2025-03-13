package com.shrikant.paypay.application

import com.shrikant.paypay.application.view.di.core.app.AppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.HasAndroidInjector

class PaypayApp : DaggerApplication(), HasAndroidInjector {

    override fun onCreate() {
        AppComponent.Injector.init(this)
        super.onCreate()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return AppComponent.Injector.getAppComponent()
    }
}