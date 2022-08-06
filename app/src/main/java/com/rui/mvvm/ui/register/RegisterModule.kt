package com.rui.mvvm.ui.register

import com.rui.base.di.scopes.FragmentScope
import dagger.Binds
import dagger.Module

@Module(includes = [RegisterModuleBinds::class])
class RegisterModule {

}

@Module
private abstract class RegisterModuleBinds {

    @Binds
    @FragmentScope
    internal abstract fun fragment(fragment: RegisterFragment): RegisterFragment

}