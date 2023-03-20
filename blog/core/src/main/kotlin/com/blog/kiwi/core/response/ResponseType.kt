package com.blog.kiwi.core.response

import org.springframework.http.HttpStatus

enum class ResponseType(
    val code: String,
    val httpStatus: HttpStatus,
    val defaultMessage: String
) {
    SUCCESS("100", HttpStatus.OK, "success"),
    NOT_SUPPORTED("201", HttpStatus.BAD_REQUEST, "Not Supported"),
    CLIENT_ERROR("301", HttpStatus.BAD_REQUEST, "Client Call Error"),
    UNKNOWN("999", HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"),
    ;
}