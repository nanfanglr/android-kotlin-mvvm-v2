package com.rui.mvvm.ui.me

import android.view.LayoutInflater
import android.view.ViewGroup
import com.rui.mvvm.databinding.FragmentMeBinding
import com.rui.base.fragment.BaseDaggerFragment

class MeFragment : BaseDaggerFragment<FragmentMeBinding, MeViewModel>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMeBinding =
        FragmentMeBinding.inflate(inflater, container, false)

    override fun getVMClass(): Class<MeViewModel> = MeViewModel::class.java
}