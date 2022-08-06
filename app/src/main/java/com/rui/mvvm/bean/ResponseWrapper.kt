package com.rui.mvvm.bean

data class ResponseWrapper<T>(
    var errorCode: Int,
    var errorMsg: String?,
    var data: MutableList<T>?
)
