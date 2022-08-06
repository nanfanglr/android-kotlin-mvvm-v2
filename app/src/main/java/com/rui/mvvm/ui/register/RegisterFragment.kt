package com.rui.mvvm.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rui.mvvm.databinding.FragmentRegisterBinding
import com.rui.base.fragment.BaseDaggerFragment

class RegisterFragment : BaseDaggerFragment<FragmentRegisterBinding, RegisterViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegisterBinding =
        FragmentRegisterBinding.inflate(inflater, container, false)

    override fun getVMClass(): Class<RegisterViewModel> = RegisterViewModel::class.java

}