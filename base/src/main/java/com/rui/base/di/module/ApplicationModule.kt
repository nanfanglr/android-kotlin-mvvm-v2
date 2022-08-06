package com.rui.base.di.module

import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import com.rui.base.BaseApplication
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Singleton
    @Provides
    fun provideApplicationContext(app: BaseApplication): Context {
        return app
    }

    @Singleton
    @Provides
    @Named("debug")
    fun provideIsDebug(app: BaseApplication): Boolean {
        return app.isDebug()
    }

    @Provides
    @Singleton
    fun providesResources(application: BaseApplication): Resources {
        return application.resources
    }

    @Provides
    @Singleton
    fun providesAssetManager(resources: Resources): AssetManager {
        return resources.assets
    }

}
