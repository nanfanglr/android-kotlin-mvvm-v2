package com.rui.mvvm.ui.board

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.rui.mvvm.databinding.FragmentBoardBinding
import com.rui.mvvm.ui.home.HomeAdapter
import com.rui.base.fragment.BaseDaggerFragment
import javax.inject.Inject

class BoardFragment : BaseDaggerFragment<FragmentBoardBinding, BoardViewModel>() {

    @Inject
    lateinit var adapter: HomeAdapter

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvData.adapter = adapter
        binding.rvData.layoutManager = linearLayoutManager
        viewModel.bannerLiveData.observe(viewLifecycleOwner) {
            adapter.setList(it)
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBoardBinding =
        FragmentBoardBinding.inflate(inflater, container, false)

    override fun getVMClass(): Class<BoardViewModel> = BoardViewModel::class.java


}