package com.rui.retrofit2.intercepter

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

private const val DEFAULT_RESPONSE_CODE = 200
private val DEFAULT_MEDIA_TYPE = "application/json".toMediaType()
private val DEFAULT_PROTOCOL = Protocol.HTTP_2

typealias URLResponse = MutableMap<String, (Request) -> Response>

fun Response(
    request: Request,
    responseBody: ResourceFile,
    mediaType: MediaType = DEFAULT_MEDIA_TYPE,
    responseCode: Int = DEFAULT_RESPONSE_CODE,
    protocol: Protocol = DEFAULT_PROTOCOL
): Response {
    return Response(
        request,
        responseBody.readText(),
        mediaType,
        responseCode,
        protocol
    )
}

fun Response(
    request: Request,
    responseBody: String = "",
    mediaType: MediaType = DEFAULT_MEDIA_TYPE,
    responseCode: Int = DEFAULT_RESPONSE_CODE,
    protocol: Protocol = DEFAULT_PROTOCOL
): Response {
    return Response.Builder()
        .addHeader("Content-Type", mediaType.toString())
        .body(responseBody.toResponseBody(mediaType))
        .protocol(protocol)
        .code(responseCode)
        .request(request)
        .build()
}