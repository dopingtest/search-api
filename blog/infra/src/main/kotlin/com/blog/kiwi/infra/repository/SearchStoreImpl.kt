package com.blog.kiwi.infra.repository

import com.blog.kiwi.domain.entity.PopularSearchKeyword
import com.blog.kiwi.domain.store.SearchStore
import com.blog.kiwi.infra.repository.command.PopularSearchKeywordCommandRepository
import org.springframework.stereotype.Repository

@Repository
class SearchStoreImpl(
    private val popularSearchKeywordCommandRepository: PopularSearchKeywordCommandRepository
): SearchStore {

    override fun savePopularSearchKeyword(popularSearchKeywords: List<PopularSearchKeyword>) {
        popularSearchKeywordCommandRepository.saveAll(popularSearchKeywords)
    }
}