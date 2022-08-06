package com.rui.mvvm.ui.upgrade

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rui.mvvm.utils.ApKUtil
import com.rui.filedownload.DownloadRepository
import com.rui.filedownload.FileUtils
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class AppUpgradeViewModel @Inject constructor(
    private var downloadRepository: DownloadRepository,
) : ViewModel() {

    val dismissDialog = MutableLiveData<Unit>()
    val progressLiveData = MutableLiveData<Int>()

    fun downLoadApk(context: Context) {
        viewModelScope.launch {
            try {
                downloadRepository.downLoad(
                    "http://59.110.162.30/v450_imooc_updater.apk",
                    FileUtils.createTargetFile(context, "imooc_updater.apk"),
                    onProgress = {
                        progressLiveData.postValue(it) },
                    onFinish = {
                        dismissDialog.postValue(Unit)
                        ApKUtil.installApk(context, it)
                    },
                    onError = {
                        dismissDialog.postValue(Unit)
                    }
                )
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }
}