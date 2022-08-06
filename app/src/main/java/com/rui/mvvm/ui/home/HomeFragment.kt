package com.rui.mvvm.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.rui.mvvm.R
import com.rui.mvvm.databinding.FragmentHomeBinding
import com.rui.mvvm.ui.upgrade.AppUpgradeDialogFragment
import com.rui.base.fragment.BaseDaggerFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeFragment : BaseDaggerFragment<FragmentHomeBinding, HomeViewModel>() {

    @Inject
    lateinit var adapter: HomeAdapter

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTabLayout()
//        checkIfNeedUpgrade()
    }

    private fun initTabLayout() {
        //Please learn more from https://cloud.tencent.com/developer/article/1820734
        val tabLayout = binding.tabs
        val viewPager = binding.viewPager

        viewPager.adapter = HomePagerAdapter(this)

        // Set the icon and text for each tab
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setIcon(getTabIcon(position))
            tab.text = getTabTitle(position)
        }.attach()
    }

    private fun checkIfNeedUpgrade() {
        viewLifecycleOwner.lifecycleScope.launch {
            delay(2000)
            AppUpgradeDialogFragment().show(childFragmentManager, "AppUpgradeDialogFragment")
        }
    }

    private fun getTabIcon(position: Int): Int {
        return when (position) {
            FIRST_CHILD -> R.drawable.ic_home
            SECOND_CHILD -> R.drawable.ic_feedback
            else -> throw IndexOutOfBoundsException()
        }
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            FIRST_CHILD -> getString(R.string.FirstChild)
            SECOND_CHILD -> getString(R.string.SecondChild)
            else -> null
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding =
        FragmentHomeBinding.inflate(inflater, container, false)

    override fun getVMClass(): Class<HomeViewModel> = HomeViewModel::class.java


}