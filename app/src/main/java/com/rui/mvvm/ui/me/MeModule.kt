package com.rui.mvvm.ui.me

import com.rui.base.di.scopes.FragmentScope
import dagger.Binds
import dagger.Module

@Module(includes = [MeModuleBinds::class])
class MeModule {

}

@Module
private abstract class MeModuleBinds {

    @Binds
    @FragmentScope
    internal abstract fun fragment(fragment: MeFragment): MeFragment

}