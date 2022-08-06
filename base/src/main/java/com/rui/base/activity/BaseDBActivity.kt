package com.rui.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding


open abstract class BaseDBActivity<VB : ViewBinding>
    : AppCompatActivity() {

    /**
     * 当前页面的ViewBinding类，由databinding自动生成
     */
    protected lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
    }

    /**
     * Init ViewBinding,using code like this :
     * ActivityXxxBinding.inflate(layoutInflater)
     * @return
     */
    protected abstract fun getViewBinding(): VB

}