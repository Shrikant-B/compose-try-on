package com.shrikant.paypay.application.view.di.core.app.module

import android.content.Context
import com.shrikant.paypay.application.PaypayApp
import dagger.Module
import dagger.Provides

@Module
object AppModule {

    @Provides
    fun provideContext(application: PaypayApp): Context {
        return application
    }
}