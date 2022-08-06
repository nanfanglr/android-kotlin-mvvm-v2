package com.rui.base.fragment

import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

open abstract class BaseVMFragment<
        DB : ViewBinding,
        VM : ViewModel>
    : BaseDBFragment<DB>() {

    protected val viewModel: VM by lazy {
        obtainViewModel(getVMClass())
    }

    /**
     * 获取ViewModelClass
     *
     * @return
     */
    protected abstract fun getVMClass(): Class<VM>

    /**
     * 获取ViewModel实例
     *
     * @param modelClass
     * @return
     */
    protected abstract fun obtainViewModel(modelClass: Class<VM>): VM

}

