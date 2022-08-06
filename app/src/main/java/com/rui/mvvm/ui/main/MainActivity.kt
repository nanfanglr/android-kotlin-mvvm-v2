package com.rui.mvvm.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.rui.mvvm.R
import com.rui.mvvm.databinding.ActivityMainBinding
import com.rui.mvvm.ui.home.HomeFragment
import com.rui.mvvm.ui.board.BoardFragment
import com.rui.mvvm.ui.me.MeFragment
import com.rui.base.activity.BaseDaggerActivity


class MainActivity : BaseDaggerActivity<ActivityMainBinding, MainViewModel>() {

    private var currentFragment: Fragment? = null

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun getVMClass(): Class<MainViewModel> = MainViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.mainContainer
        initView()
    }

    private fun initView() {
        binding.bottomNav.setOnNavigationItemSelectedListener {
            onTabItemSelected(it.itemId)
            true
        }

        // 初始化页面需要手动调用一下切换状态的方法
        onTabItemSelected(binding.bottomNav.selectedItemId)
    }

    private fun onTabItemSelected(id: Int) {
        var fragment: Fragment? = supportFragmentManager.findFragmentByTag(id.toString())
        if (fragment != null) {
            if (fragment === currentFragment) return
            supportFragmentManager.beginTransaction().show(fragment).commit()
        } else {
            fragment = createFragment(id)
            fragment?.let {
                supportFragmentManager.beginTransaction().add(
                    R.id.main_container, it, id.toString()
                ).commit()
            }
        }
        currentFragment?.let {
            supportFragmentManager.beginTransaction().hide(it).commit()
        }
        currentFragment = fragment
    }

    private fun createFragment(id: Int): Fragment? = when (id) {
        R.id.home -> HomeFragment()
        R.id.board -> BoardFragment()
        R.id.me -> MeFragment()
        else -> null
    }

}