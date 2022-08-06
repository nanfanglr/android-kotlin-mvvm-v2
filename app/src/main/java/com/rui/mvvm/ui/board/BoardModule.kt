package com.rui.mvvm.ui.board

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.rui.base.di.scopes.FragmentScope
import dagger.Binds
import dagger.Module
import dagger.Provides


@Module(includes = [LeaderBoardModuleBinds::class])
class LeaderBoardModule {
    @Provides
    internal fun providesLayoutManager(context: Context): LinearLayoutManager =
        LinearLayoutManager(context)
}

@Module
private abstract class LeaderBoardModuleBinds {

    @Binds
    @FragmentScope
    abstract fun fragment(fragment: BoardFragment): BoardFragment

}