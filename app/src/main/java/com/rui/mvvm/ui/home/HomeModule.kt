package com.rui.mvvm.ui.home

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.rui.mvvm.ui.firstchild.FirstChildFragment
import com.rui.mvvm.ui.firstchild.FirstChildModule
import com.rui.mvvm.ui.upgrade.AppUpgradeDialogFragment
import com.rui.mvvm.ui.upgrade.AppUpgradeModule
import com.rui.base.di.scopes.ChildFragmentScope
import com.rui.base.di.scopes.FragmentScope
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module(includes = [HomeModuleBinds::class])
class HomeModule {

    @Provides
    internal fun providesLayoutManager(context: Context): LinearLayoutManager =
        LinearLayoutManager(context)

}

@Module
private abstract class HomeModuleBinds {

    @Binds
    @FragmentScope
    abstract fun fragment(fragment: HomeFragment): HomeFragment

    @ChildFragmentScope
    @ContributesAndroidInjector(modules = [FirstChildModule::class])
    abstract fun homeChildFragmentInjector(): FirstChildFragment

    @ChildFragmentScope
    @ContributesAndroidInjector(modules = [AppUpgradeModule::class])
    abstract fun appUpgradeDialogFragmentInjector(): AppUpgradeDialogFragment

}