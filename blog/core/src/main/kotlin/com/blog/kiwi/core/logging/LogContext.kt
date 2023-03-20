package com.blog.kiwi.core.logging

object LogContext {

    private val CONTEXT_HOLDER: ThreadLocal<PayloadContext> = ThreadLocal<PayloadContext>()
    private val payloadContext: PayloadContext = PayloadContext()

    data class PayloadContext(
        var clientIp: String = "",
        var queryString: String = "",
        var requestUri: String = ""
    )

    fun getPayloadContext(): PayloadContext {
        return CONTEXT_HOLDER.get() ?: payloadContext
    }

    fun setPayloadContext(payloadContext: PayloadContext) {
        CONTEXT_HOLDER.set(payloadContext)
    }

    fun setClientIp(clientIp: String?) {
        if (clientIp == null) {
            return
        }
        val payloadContext: PayloadContext = LogContext.payloadContext
        payloadContext.clientIp = clientIp
    }

    fun setQueryString(queryString: String?) {
        if (queryString == null) {
            return
        }
        val payloadContext: PayloadContext = LogContext.payloadContext
        payloadContext.queryString = queryString
    }

    fun setRequestUri(requestUri: String?) {
        if (requestUri == null) {
            return
        }
        val payloadContext: PayloadContext = LogContext.payloadContext
        payloadContext.requestUri = requestUri
    }

    fun clear() {
        CONTEXT_HOLDER.remove()
    }
}