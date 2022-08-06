package com.rui.mvvm.ui.firstchild

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rui.mvvm.R
import com.rui.mvvm.bean.TopArticleBean
import com.rui.mvvm.databinding.ItemFirstChildBinding
import javax.inject.Inject

class FirstChildAdapter @Inject constructor() :

    BaseQuickAdapter<TopArticleBean, BaseViewHolder>(R.layout.item_first_child) {

    override fun convert(holder: BaseViewHolder, item: TopArticleBean) {
        val bind = ItemFirstChildBinding.bind(holder.itemView)
        bind.tvTitle.text = item.title
        bind.tvAuthor.text = item.author
        bind.tvTime.text = item.niceShareDate
    }
}