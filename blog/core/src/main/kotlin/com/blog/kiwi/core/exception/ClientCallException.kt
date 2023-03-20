package com.blog.kiwi.core.exception

import com.blog.kiwi.core.response.ResponseType


class ClientCallException: KiwiException {

    constructor(responseType: ResponseType, customErrorMsg: String? = null): super(responseType, responseType.defaultMessage)
}