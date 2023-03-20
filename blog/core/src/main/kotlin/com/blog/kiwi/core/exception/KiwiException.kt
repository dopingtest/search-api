package com.blog.kiwi.core.exception

import com.blog.kiwi.core.response.ResponseType

abstract class KiwiException(
    private var responseType: ResponseType,
    private var customErrorMsg: String = ""
): RuntimeException(customErrorMsg) {
    fun getResponseType() = responseType
    fun getCustomErrorMsg() = customErrorMsg
}