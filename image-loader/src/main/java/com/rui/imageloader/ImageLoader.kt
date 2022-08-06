package com.rui.imageloader

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import androidx.annotation.DrawableRes

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey


/**
 * Class works as our image loading library wrapper.
 */
object ImageLoader {

    /**
     * 自定义loading、error、null图片的RequestOptions
     * DiskCacheStrategy.ALL 使用DATA和RESOURCE缓存远程数据，仅使用RESOURCE来缓存本地数据。
     * DiskCacheStrategy.NONE 不使用磁盘缓存
     * DiskCacheStrategy.DATA 在资源解码前就将原始数据写入磁盘缓存
     * DiskCacheStrategy.RESOURCE 在资源解码后将数据写入磁盘缓存，即经过缩放等转换后的图片资源。
     * DiskCacheStrategy.AUTOMATIC 根据原始图片数据和资源编码策略来自动选择磁盘缓存策略。
     * @param loadingDrawable
     * @return
     */
    @SuppressLint("CheckResult")
    private fun getOption(
        @DrawableRes loadingDrawable: Int?,
        @DrawableRes errorDrawable: Int?,
        @DrawableRes nullDrawable: Int?,
    ): RequestOptions = RequestOptions().apply {
        loadingDrawable?.let {
            this.placeholder(loadingDrawable)//加载中默认图片
        }
        errorDrawable?.let {
            this.error(errorDrawable)//  加载错误默认图片
        }
        nullDrawable?.let {
            this.fallback(nullDrawable)
        }
    }


    private fun createCacheOptions(
        skipCache: Boolean = false,
        cacheStrategy: DiskCacheStrategy = DiskCacheStrategy.ALL,
        timeStamp: String = 0.toString()
    ): RequestOptions = RequestOptions()
        .skipMemoryCache(skipCache)
        .diskCacheStrategy(cacheStrategy)
        .signature(ObjectKey(timeStamp))

    private fun displayImageBase(
        context: Context, url: String?,
        imageView: ImageView,
        options: RequestOptions
    ) {
        Glide.with(context)
            .load(url)
            .apply(options)
            .apply(createCacheOptions())
            .into(imageView)

    }

    fun displayImage(
        context: Context,
        url: String,
        imageView: ImageView,
        @DrawableRes loadingDrawable: Int? = null,
        @DrawableRes errorDrawable: Int? = null,
        @DrawableRes nullDrawable: Int? = null,
    ) {
        displayImageBase(
            context,
            url,
            imageView,
            getOption(loadingDrawable, errorDrawable, nullDrawable)
        )
    }

}
