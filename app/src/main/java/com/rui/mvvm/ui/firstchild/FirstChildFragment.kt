package com.rui.mvvm.ui.firstchild

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.rui.mvvm.bean.TopArticleBean
import com.rui.mvvm.databinding.FragmentFirstChildBinding
import com.rui.base.fragment.BaseDaggerFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class FirstChildFragment : BaseDaggerFragment<FragmentFirstChildBinding, FirstChildViewModel>() {

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var adapter: FirstChildAdapter

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFirstChildBinding =
        FragmentFirstChildBinding.inflate(inflater, container, false)

    override fun getVMClass(): Class<FirstChildViewModel> = FirstChildViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvData.adapter = adapter
        binding.rvData.layoutManager = linearLayoutManager

        /**
         * use liveData update view
         */
//        viewModel.topArticlesLiveData.observe(viewLifecycleOwner) {
//            adapter.setList(it)
//        }

        /**
         * use stateFlow update view
         * learn more, click link:
         * https://mp.weixin.qq.com/s/tqNLTbBB2o4O_vLlAbf8hw
         * https://mp.weixin.qq.com/s/o61NDIptP94X4HspKwiR2w
         * https://mp.weixin.qq.com/s/o2bu9Fl2UMnXgs6BIb-0KQ
         * https://mp.weixin.qq.com/s/BICq06PTlseX60my9KWpSw
         * https://segmentfault.com/a/1190000041683454?utm_source=sf-similar-article
         * https://www.jianshu.com/p/5392573692ba
         * https://blog.csdn.net/androiddevs/article/details/119951992
         */
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.topArticlesStateFlow.collect {
                    when (it) {
                        is UiState.Loading -> {
                            Timber.d("show loading view")
                        }
                        is UiState.Success<List<TopArticleBean>?> -> {
                            Timber.d("update list view")
                            updateView(it.data)
                        }
                        is UiState.Empty -> {
                            Timber.d("show empty view")
                        }
                        is UiState.Error -> {
                            Timber.d("show error view")
                        }
                    }
                }
            }
        }
    }

    private fun updateView(data: List<TopArticleBean>?) {
        data?.let { adapter.setList(data) }
    }
}