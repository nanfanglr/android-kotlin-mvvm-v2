package com.rui.mvvm.ui.upgrade

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rui.mvvm.databinding.FragmentAppUpgradeBinding

import com.rui.base.dialogfragment.BaseDaggerDialogFragment

class AppUpgradeDialogFragment : BaseDaggerDialogFragment<
        FragmentAppUpgradeBinding,
        AppUpgradeViewModel>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAppUpgradeBinding =
        FragmentAppUpgradeBinding.inflate(inflater, container, false)

    override fun getVMClass(): Class<AppUpgradeViewModel> = AppUpgradeViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickEvent()
    }

    private fun initClickEvent() {
        binding.tvUpdate.setOnClickListener {
            it.isEnabled = false
            viewModel.downLoadApk(requireContext().applicationContext)
        }
        viewModel.dismissDialog.observe(viewLifecycleOwner) {
            this.dismiss()
        }
        viewModel.progressLiveData.observe(viewLifecycleOwner) {
            binding.tvUpdate.text = "${it}%"
        }
    }
}