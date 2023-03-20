package com.blog.kiwi.api.exception

import com.blog.kiwi.core.exception.KiwiException
import com.blog.kiwi.core.response.CommonResponse
import com.blog.kiwi.core.response.ResponseType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandlerAdvice {

    @ExceptionHandler(KiwiException::class)
    fun apiException(e: KiwiException): ResponseEntity<CommonResponse<*>> {
        val body = CommonResponse.fail(e.getResponseType(), e.getCustomErrorMsg())
        return ResponseEntity.status(e.getResponseType().httpStatus).body(body)
    }

    @ExceptionHandler(Exception::class)
    fun exception(e: Exception): ResponseEntity<CommonResponse<*>> {
        val body = CommonResponse.fail(ResponseType.UNKNOWN, e.stackTraceToString())
        return ResponseEntity.status(ResponseType.UNKNOWN.httpStatus).body(body)
    }
}