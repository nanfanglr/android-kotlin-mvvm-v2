package com.rui.filedownload

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface DownloadService {
    //It's import that this annotation must be used,
    // otherwise retrofit cannot  provide the progress.
    @Streaming
    @GET
    suspend fun download(
        @Url url: String?
    ): Response<ResponseBody>
}