package com.blog.kiwi.core.response

import com.blog.kiwi.core.util.getCurrentTimestamp

class CommonResponse<T> {

    var timestamp: Long = 0
    var code: String? = null
    var message: String? = null
    var result: T? = null

    companion object {
        fun success(): CommonResponse<*> {
            return success("", "")
        }

        fun <T> success(result: T): CommonResponse<T> {
            return success(result, "")
        }

        fun <T> success(result: T, message: String): CommonResponse<T> {
            return CommonResponse<T>().apply {
                this.timestamp = getCurrentTimestamp()
                this.code = ResponseType.SUCCESS.code
                this.message = message
                this.result = result
            }
        }

        fun fail(responseType: ResponseType, message: String): CommonResponse<*> {
            return CommonResponse<String>().apply {
                this.timestamp = getCurrentTimestamp()
                this.code = responseType.code
                this.message = message
                this.result = ""
            }
        }
    }
}