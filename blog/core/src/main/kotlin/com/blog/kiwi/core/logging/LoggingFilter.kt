package com.blog.kiwi.core.logging

import com.blog.kiwi.core.constant.SEARCH_BLOG
import com.fasterxml.jackson.databind.ObjectMapper
import net.logstash.logback.argument.StructuredArguments.kv
import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory
import org.slf4j.MarkerFactory
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class LoggingFilter(
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {

    companion object FilterPolicy {
        const val BLOG = "BLOG"
    }

    private val globalLogger = LoggerFactory.getLogger(javaClass)
    private val blogLogger = LoggerFactory.getLogger(BLOG.lowercase())
    private val blogMarker = MarkerFactory.getMarker(BLOG)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val requestQueryString = if (StringUtils.isNotEmpty(request.queryString)) "?" + request.queryString else ""

        LogContext.apply {
            setPayloadContext(getPayloadContext())
            setQueryString(request.requestURI + requestQueryString)
            setClientIp(getClientIp(request))
            setRequestUri(request.requestURI)
        }

        val requestURI = request.requestURI
        if (requestURI.startsWith(SEARCH_BLOG)) {
            globalLogger.info(blogMarker, "{}", kv("request", LogContext.getPayloadContext()))
            blogLogger.info(blogMarker, "{}", objectMapper.writeValueAsString(LogContext.getPayloadContext()))
        }

        filterChain.doFilter(request, response)

        LogContext.clear()
    }

    private fun getClientIp(request: HttpServletRequest): String? {
        var clientIp = request.getHeader("X-Forwarded-For")
        if (clientIp == null) {
            clientIp = request.getHeader("Proxy-Client-IP")
        }
        if (clientIp == null) {
            clientIp = request.getHeader("WL-Proxy-Client-IP")
        }
        if (clientIp == null) {
            clientIp = request.getHeader("HTTP_CLIENT_IP")
        }
        if (clientIp == null) {
            clientIp = request.getHeader("HTTP_X_FORWARDED_FOR")
        }
        if (clientIp == null) {
            clientIp = request.remoteAddr
        }
        return clientIp
    }
}