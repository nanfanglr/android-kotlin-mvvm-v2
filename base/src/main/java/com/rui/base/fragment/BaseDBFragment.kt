package com.rui.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

open abstract class BaseDBFragment<DB : ViewBinding> : Fragment() {

    private var _binding: DB? = null

    //This property is only valid between onCreateView and onDestroyView.
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding(inflater,container)
        return binding.root
    }

    /**
     * init viewBinding using code like this:
     * FragmentBlankBinding.inflate(inflater, container, false)
     *
     * @return
     */
    protected abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): DB

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}