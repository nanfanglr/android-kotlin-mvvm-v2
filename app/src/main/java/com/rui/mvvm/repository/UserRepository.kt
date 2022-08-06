package com.rui.mvvm.repository

import com.rui.mvvm.bean.BannerBean
import com.rui.mvvm.retrofit.Service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(private val service: Service) {

    suspend fun getBanner(): List<BannerBean>? =
        withContext(Dispatchers.IO) {
            service.getBanner().data
        }

}