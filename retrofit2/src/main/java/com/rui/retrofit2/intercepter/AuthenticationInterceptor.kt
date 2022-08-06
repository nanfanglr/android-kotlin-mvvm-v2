package com.rui.retrofit2.intercepter

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthenticationInterceptor @Inject constructor() : Interceptor {

    private val API_AUTH_TOKEN = "api_auth_token"

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            .addHeader(API_AUTH_TOKEN, "propertiesManager.apiToken")
            .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
            .addHeader("Accept-Encoding", "gzip, deflate")
            .addHeader("Connection", "keep-alive")
            .addHeader("Accept", "*/*")
            .build()
        return chain.proceed(request)

    }
}