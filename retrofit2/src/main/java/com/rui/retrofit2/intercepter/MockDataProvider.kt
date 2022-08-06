package com.rui.retrofit2.intercepter

class MockDataProvider {

    fun getMockResponses(): URLResponse {
        return mutableMapOf(
            "url-1111" to { Response(it, "file") },
            "url-2222" to { Response(it, "file") },
            "url-3333" to { Response(it, "file") },
        )
    }
}