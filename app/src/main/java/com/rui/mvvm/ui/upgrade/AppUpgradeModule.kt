package com.rui.mvvm.ui.upgrade

import com.rui.base.di.scopes.FragmentScope
import dagger.Binds
import dagger.Module

@Module(includes = [AppUpgradeModuleBinds::class])
class AppUpgradeModule {
}

@Module
private abstract class AppUpgradeModuleBinds {

    @Binds
    @FragmentScope
    internal abstract fun fragment(fragment: AppUpgradeDialogFragment): AppUpgradeDialogFragment

}