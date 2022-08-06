package com.rui.mvvm.ui.board

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rui.mvvm.bean.BannerBean
import com.rui.mvvm.repository.UserRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class BoardViewModel @Inject constructor(
    private var repository: UserRepository
) : ViewModel() {

    val bannerLiveData = MutableLiveData<List<BannerBean>>()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val banner = try {
                repository.getBanner()
            } catch (exception: Exception) {
                exception.printStackTrace()
                null
            }
            banner?.let { bannerLiveData.postValue(it) }
        }
    }

}