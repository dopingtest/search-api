package com.blog.kiwi.infra.adapter

import com.blog.kiwi.core.constant.SearchProvider
import com.blog.kiwi.core.constant.SortCondition
import com.blog.kiwi.domain.adapter.SearchAdapter
import com.blog.kiwi.domain.spec.SearchBlogResponseSpec
import com.blog.kiwi.infra.mapper.SearchClientMapper
import com.blog.kiwi.infra.properties.SearchProperties
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component(value = SearchProvider.NAVER)
class NaverSearchAdapterImpl(
    private val webClient: WebClient,
    private val objectMapper: ObjectMapper,
    private val searchClientMapper: SearchClientMapper,
    private val searchProperties: SearchProperties,
): SearchAdapter {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun searchBlog(query: String, sort: SortCondition, page :Int, size: Int): SearchBlogResponseSpec {
        // 일단은 Fallback 으로 default value 제공
        return SearchBlogResponseSpec.defaultInstance()
    }
}