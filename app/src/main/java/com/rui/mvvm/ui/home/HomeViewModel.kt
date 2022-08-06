package com.rui.mvvm.ui.home

import androidx.lifecycle.ViewModel
import com.rui.mvvm.repository.UserRepository
import com.rui.filedownload.DownloadRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private var repository: UserRepository,
    private var downloadRepository: DownloadRepository,
) : ViewModel() {

}