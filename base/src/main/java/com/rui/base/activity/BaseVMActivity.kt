package com.rui.base.activity

import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

open abstract class BaseVMActivity<DB : ViewBinding, VM : ViewModel>
    : BaseDBActivity<DB>() {

    protected val viewModel: VM by lazy {
        obtainViewModel(getVMClass())
    }

    /**
     * 获取ViewModel实例
     *
     * @param modelClass
     * @return
     */
    protected abstract fun obtainViewModel(modelClass: Class<VM>): VM

    /**
     * 获取ViewModelClass
     *
     * @return
     */
    protected abstract fun getVMClass(): Class<VM>


}