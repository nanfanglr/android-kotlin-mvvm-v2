package com.rui.retrofit2.intercepter

import okhttp3.Interceptor
import okhttp3.Response

class MockDataInterceptor constructor(private val mockResponses: URLResponse) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        mockResponses[request.url.encodedPath]?.let {
            return it.invoke(request)
        }

        throw Exception("Mock data not provider for ${request.url}")
    }
}
