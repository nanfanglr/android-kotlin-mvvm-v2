package com.rui.base.network.ApiErro

/**
 * 自定义的api错误类，以方便统一处理
 */
class ApiException(val errorCode: Int, msg: String) : RuntimeException(msg)