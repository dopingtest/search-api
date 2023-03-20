package com.blog.kiwi.domain.service

import com.blog.kiwi.core.constant.SearchProvider
import com.blog.kiwi.core.constant.SortCondition
import com.blog.kiwi.core.exception.NotSupportedSearchProviderException
import com.blog.kiwi.domain.adapter.SearchAdapter
import com.blog.kiwi.domain.spec.SearchBlogResponseSpec
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class SearchService(
    private val searchAdapters: Map<String, SearchAdapter>
) {

    fun searchBlog(
        query: String,
        provider: String,
        sort: SortCondition,
        page: Int,
        size: Int
    ): SearchBlogResponseSpec {
        val searchAdapter = searchAdapters[provider] ?: throw NotSupportedSearchProviderException(provider)

        val result = try {
            searchAdapter.searchBlog(query, sort, page, size)
        } catch (e: Exception) {
            // fallback
            val fallbackAdapter = searchAdapters[SearchProvider.NAVER] ?: throw NotSupportedSearchProviderException(provider = SearchProvider.NAVER)
            fallbackAdapter.searchBlog(query, sort, page, size)
        }

        return result
    }
}