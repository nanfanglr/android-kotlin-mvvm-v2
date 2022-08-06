package com.rui.base.fragment

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import java.lang.IllegalStateException
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Fragment 加载viewBinding的扩展方法
 * 属性委托实现
 * private val binding by viewBindings (FragmentXXXBinding::bind)
 * private val binding by viewBindings { FragmentFirstChildBinding.bind(it) }
 */
fun <VB : ViewBinding> Fragment.viewBindings(viewBindingFactory: (View) -> VB) =
    FragmentViewBindingDelegate(this, viewBindingFactory)

/**
 * viewBinding 属性委托的具体实现
 */
class FragmentViewBindingDelegate<VB : ViewBinding>(
    val fragment: Fragment,
    val viewBindingFactory: (View) -> VB
) : ReadOnlyProperty<Fragment, VB> {

    private var binding: VB? = null

    init {
        addLifecycleObserver()
    }

    private fun addLifecycleObserver() {
        Handler(Looper.getMainLooper()).post {
            //给fragment添加生命周期监听
            fragment.lifecycle.addObserver(lifecycleObserver())
        }
    }

    private fun lifecycleObserver() = object : DefaultLifecycleObserver {
        //创建Fragment的view生命周期liveData监听，注意与fragment的生命周期监听区分
        //这个监听是用来第一时间设置viewLifecycleOwner后，对viewLifecycleOwner进行监听
        val viewLifecycleOwnerLiveDataObserver = Observer<LifecycleOwner> {
            //如果没有owner，则返回
            val viewLifecycleOwner = it ?: return@Observer
            //
            viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                override fun onDestroy(owner: LifecycleOwner) {
                    //处理fragment onDestroy 后 binding释放
                    binding = null
                }
            })
        }

        override fun onCreate(owner: LifecycleOwner) {
            fragment.viewLifecycleOwnerLiveData.observeForever(viewLifecycleOwnerLiveDataObserver)
        }

        override fun onDestroy(owner: LifecycleOwner) {
            fragment.viewLifecycleOwnerLiveData.removeObserver(viewLifecycleOwnerLiveDataObserver)
        }

    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): VB {
        val binding = binding
        if (binding != null) return binding
        val lifecycle = fragment.viewLifecycleOwner.lifecycle
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            throw IllegalStateException("Should not attempt to get bindings when Fragment views are destroy.")
        }
        return viewBindingFactory(thisRef.requireView()).also { this.binding = it }
    }
}