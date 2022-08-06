package com.rui.mvvm.ui.main

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.rui.mvvm.ui.home.HomeFragment
import com.rui.mvvm.ui.home.HomeModule
import com.rui.mvvm.ui.board.BoardFragment
import com.rui.mvvm.ui.board.LeaderBoardModule
import com.rui.mvvm.ui.me.MeFragment
import com.rui.mvvm.ui.me.MeModule
import com.rui.mvvm.ui.register.RegisterFragment
import com.rui.mvvm.ui.register.RegisterModule
import com.rui.base.di.scopes.ActivityContext
import com.rui.base.di.scopes.ActivityScope
import com.rui.base.di.scopes.FragmentScope
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [MainModuleBinds::class])
class MainModule {

}

@Module
private abstract class MainModuleBinds {

    @FragmentScope
    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun homeFragmentInjector(): HomeFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [LeaderBoardModule::class])
    abstract fun leaderBoardFragmentInjector(): BoardFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [MeModule::class])
    abstract fun meFragmentInjector(): MeFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [RegisterModule::class])
    abstract fun registerFragmentInjector(): RegisterFragment

    @Binds
    @ActivityScope
    abstract fun activity(activity: MainActivity): FragmentActivity

    @Binds
    @ActivityScope
    @ActivityContext
    abstract fun activityContext(activity: FragmentActivity): Context
}