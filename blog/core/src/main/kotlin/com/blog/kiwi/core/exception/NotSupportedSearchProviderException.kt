package com.blog.kiwi.core.exception

import com.blog.kiwi.core.response.ResponseType


class NotSupportedSearchProviderException: KiwiException {

    constructor(provider: String): super(ResponseType.NOT_SUPPORTED, "$provider is Not Supported")

    constructor(responseType: ResponseType, customErrorMsg: String = ""): super(responseType, customErrorMsg)
}