package com.rui.mvvm.ui.home

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rui.mvvm.R
import com.rui.mvvm.bean.BannerBean
import com.rui.mvvm.databinding.ItemHomeBinding
import com.rui.imageloader.ImageLoader
import javax.inject.Inject

class HomeAdapter @Inject constructor() :
    BaseQuickAdapter<BannerBean, BaseViewHolder>(R.layout.item_home) {

    override fun convert(holder: BaseViewHolder, item: BannerBean) {
        val bind = ItemHomeBinding.bind(holder.itemView)
        ImageLoader.displayImage(context, item.imagePath, bind.iv)
        bind.tvTitle.text = item.title
        bind.tvDesc.text = item.url
    }
}