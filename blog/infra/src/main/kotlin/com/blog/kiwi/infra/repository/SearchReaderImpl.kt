package com.blog.kiwi.infra.repository

import com.blog.kiwi.domain.reader.SearchReader
import com.blog.kiwi.domain.spec.PopularKeywordResponseSpec
import com.blog.kiwi.infra.repository.query.PopularSearchKeywordQueryRepository
import org.springframework.stereotype.Repository

@Repository
class SearchReaderImpl(
    private val popularSearchKeywordQueryRepository: PopularSearchKeywordQueryRepository
): SearchReader {

    override fun findAllTopNPopularKeywords(limit: Int): List<PopularKeywordResponseSpec> {
        return popularSearchKeywordQueryRepository.findAllTopNPopularKeywords(limit)
    }
}