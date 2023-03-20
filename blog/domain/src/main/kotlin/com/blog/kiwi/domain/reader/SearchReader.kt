package com.blog.kiwi.domain.reader

import com.blog.kiwi.domain.spec.PopularKeywordResponseSpec

interface SearchReader {

    fun findAllTopNPopularKeywords(limit: Int = 10): List<PopularKeywordResponseSpec>
}