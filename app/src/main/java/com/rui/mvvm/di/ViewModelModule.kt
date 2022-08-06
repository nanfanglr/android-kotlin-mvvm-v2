package com.rui.mvvm.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rui.mvvm.ui.home.HomeViewModel
import com.rui.mvvm.ui.firstchild.FirstChildViewModel
import com.rui.mvvm.ui.board.BoardViewModel
import com.rui.mvvm.ui.main.MainViewModel
import com.rui.mvvm.ui.me.MeViewModel
import com.rui.mvvm.ui.register.RegisterViewModel
import com.rui.mvvm.ui.upgrade.AppUpgradeViewModel
import com.rui.base.di.scopes.ViewModelScope
import com.rui.base.vmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class ViewModelModule {

    @Binds
    @Singleton
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelScope(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelScope(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelScope(BoardViewModel::class)
    abstract fun bindLeaderBoardViewModel(viewModel: BoardViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelScope(MeViewModel::class)
    abstract fun bindMeViewModel(viewModel: MeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelScope(RegisterViewModel::class)
    abstract fun bindRegisterViewModel(viewModel: RegisterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelScope(FirstChildViewModel::class)
    abstract fun bindHomeChildViewModel(viewModel: FirstChildViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelScope(AppUpgradeViewModel::class)
    abstract fun bindAppUpgradeViewModel(viewModel: AppUpgradeViewModel): ViewModel
}