package com.rui.mvvm.ui.firstchild

import com.rui.base.di.scopes.ChildFragmentScope
import dagger.Binds
import dagger.Module

@Module(includes = [FirstChildModuleBinds::class])
class FirstChildModule {

}

@Module
abstract class FirstChildModuleBinds {

    @Binds
    @ChildFragmentScope
    abstract fun fragment(fragment: FirstChildFragment): FirstChildFragment
}
