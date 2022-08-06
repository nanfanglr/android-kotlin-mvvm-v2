package com.rui.base.fragment


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection

import javax.inject.Inject

open abstract class BaseDaggerFragment<
        DB : ViewBinding,
        VM : ViewModel>
    : BaseVMFragment<DB, VM>(), HasAndroidInjector {

    @Inject
    internal lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    /**
     * 获取ViewModel实例
     *
     * @param modelClass
     * @return
     */
    override fun obtainViewModel(modelClass: Class<VM>): VM {
        return ViewModelProvider(this, viewModelFactory).get(modelClass)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(requireContext())
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

}

