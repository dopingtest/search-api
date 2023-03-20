package com.blog.kiwi.infra.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

class KakaoSearchBlogResponse: KakaoSearchResponse<KakaoSearchBlogResponse.BlogDocument>() {

    data class BlogDocument(
        val title: String = "",
        val contents: String= "",
        val url: String = "",
        @field:JsonProperty("blogname") val blogName: String = "",
        val thumbnail: String = "",
        @field:JsonProperty("datetime") val dateTime: LocalDateTime? = null
    )
}