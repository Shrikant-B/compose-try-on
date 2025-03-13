package com.shrikant.paypay.application.data.remote.parser.di

import com.shrikant.paypay.application.data.remote.parser.ServerResponseJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object MoshiModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(ServerResponseJsonAdapterFactory())
            .build()
    }
}