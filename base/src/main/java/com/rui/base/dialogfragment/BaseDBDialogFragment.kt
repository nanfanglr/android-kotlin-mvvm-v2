package com.rui.base.dialogfragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding


open abstract class BaseDBDialogFragment<DB : ViewBinding> : DialogFragment() {

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

    /**
     * 获取屏幕的像素
     *
     * @param context
     * @return
     */
    protected fun getScreenWidthAndHight(context: Context?): IntArray {
        val wh = IntArray(2)

        val dm = context?.resources?.displayMetrics
        val wm = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(dm)

        wh[0] = dm?.widthPixels ?: 0
        wh[1] = dm?.heightPixels ?: 0
        return wh
    }


}