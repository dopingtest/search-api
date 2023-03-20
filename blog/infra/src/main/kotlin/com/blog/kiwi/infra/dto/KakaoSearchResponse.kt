package com.blog.kiwi.infra.dto

import com.fasterxml.jackson.annotation.JsonProperty

open class KakaoSearchResponse<T> {
    open var meta: Meta = Meta()
    open var documents: List<T> = mutableListOf()
}

data class Meta(
    @field:JsonProperty("total_count") val totalCount: Int = 0,
    @field:JsonProperty("pageable_count") val pageableCount: Int = 0,
    @field:JsonProperty("is_end") val isEnd: Boolean = true
)
