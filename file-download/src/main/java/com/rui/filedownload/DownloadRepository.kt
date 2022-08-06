package com.rui.filedownload

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import timber.log.Timber
import java.io.*
import javax.inject.Inject

/**
 * 断点续传请参考 https://www.jianshu.com/p/8af668a9497a
 */
class DownloadRepository @Inject constructor(private val downloadService: DownloadService) {

    suspend fun downLoad(
        url: String,
        targetFile: File,
        onStart: () -> Unit = {},
        onProgress: (Int) -> Unit = {},
        onFinish: (File) -> Unit = {},
        onError: (Exception?) -> Unit = {}
    ): Boolean? = withContext(Dispatchers.IO) {
        val responseBody = downLoadFromApi(url)
        responseBody?.let {
            FileUtils.writeInputStreamToDisk(
                it.byteStream(),
                it.contentLength(),
                targetFile,
                isActive,
                onStart,
                onProgress,
                onFinish,
                onError
            )
        }
    }

    private suspend fun downLoadFromApi(
        url: String
    ): ResponseBody? {
        Timber.d("download url is $url")
        return downloadService.download(url).body()
    }

}