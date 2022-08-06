package com.rui.mvvm

import com.rui.mvvm.di.DaggerApplicationComponent
import com.rui.base.BaseApplication
import dagger.android.AndroidInjector

class App : BaseApplication() {

    override fun onCreate() {
        super.onCreate()

    }

    override fun applicationInjector(): AndroidInjector<out BaseApplication> {
        return DaggerApplicationComponent.factory().create(this)
    }

    override fun isDebug(): Boolean {
        return BuildConfig.DEBUG
    }
}