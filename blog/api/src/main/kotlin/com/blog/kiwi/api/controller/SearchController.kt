package com.blog.kiwi.api.controller

import com.blog.kiwi.api.dto.PageResponse
import com.blog.kiwi.core.constant.SEARCH_BLOG
import com.blog.kiwi.core.constant.SortCondition
import com.blog.kiwi.core.response.CommonResponse
import com.blog.kiwi.domain.service.SearchService
import com.blog.kiwi.domain.spec.SearchBlogResponseSpec
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SearchController(
    private val searchService: SearchService
) {

    @GetMapping(SEARCH_BLOG)
    fun searchBlog(
        @RequestParam query: String,
        @RequestParam provider: String,
        @RequestParam sort: SortCondition,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): CommonResponse<PageResponse<SearchBlogResponseSpec.Item>> {
        val result = searchService.searchBlog(query, provider, sort, page, size)
        return CommonResponse.success(PageResponse.of(result.pageSpec, result.items))
    }
}