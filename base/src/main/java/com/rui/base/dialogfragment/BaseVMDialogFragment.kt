package com.rui.base.dialogfragment

import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

open abstract class BaseVMDialogFragment<
        DB : ViewBinding,
        VM : ViewModel>
    : BaseDBDialogFragment<DB>() {

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
    abstract fun obtainViewModel(modelClass: Class<VM>): VM


}

