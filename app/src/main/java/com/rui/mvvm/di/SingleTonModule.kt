package com.rui.mvvm.di

import com.rui.mvvm.retrofit.Service
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
internal class SingleTonModule {

    @Provides
    @Singleton
    internal fun providesService(retrofit: Retrofit): Service {
        return retrofit.create(Service::class.java)
    }

}