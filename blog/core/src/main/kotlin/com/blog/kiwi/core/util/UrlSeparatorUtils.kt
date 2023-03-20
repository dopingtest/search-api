package com.blog.kiwi.core.util

object UrlSeparatorUtils {

    fun getQuery(url: String): String {
        val queryParam = url.split("?").getOrNull(1)?.split("&")
            ?.map { it.split("=") }
            ?.find { it[0] == "query" }
            ?.getOrNull(1)
        return queryParam ?: ""
    }
}