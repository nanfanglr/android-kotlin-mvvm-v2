package com.rui.base.activity

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

open abstract class BaseDaggerActivity<VB : ViewBinding, VM : ViewModel>
    : BaseVMActivity<VB, VM>(), HasAndroidInjector {

    /**
     * 当前页面的ViewModel实例工厂
     */
    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    protected lateinit var androidInjector: DispatchingAndroidInjector<Any>


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    /**
     * 获取ViewModel实例
     *
     * @param modelClass
     * @return
     */
    override fun obtainViewModel(modelClass: Class<VM>): VM {
        return ViewModelProvider(this, viewModelFactory).get(modelClass)
    }


    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }


}