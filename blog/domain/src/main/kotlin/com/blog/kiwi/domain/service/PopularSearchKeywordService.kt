package com.blog.kiwi.domain.service

import com.blog.kiwi.domain.entity.PopularSearchKeyword
import com.blog.kiwi.domain.reader.SearchReader
import com.blog.kiwi.domain.spec.PopularKeywordResponseSpec
import com.blog.kiwi.domain.store.SearchStore
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class PopularSearchKeywordService(
    private val searchStore: SearchStore,
    private val searchReader: SearchReader
) {
    @Transactional
    fun sync(popularSearchKeywords: List<PopularSearchKeyword>) {
        searchStore.savePopularSearchKeyword(popularSearchKeywords)
    }

    fun findAllTopNPopularKeywords(): List<PopularKeywordResponseSpec> {
        return searchReader.findAllTopNPopularKeywords()
    }
}