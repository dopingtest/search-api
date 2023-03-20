package com.blog.kiwi.api.controller

import com.blog.kiwi.core.constant.POPULAR_SEARCH_KEYWORDS
import com.blog.kiwi.core.response.CommonResponse
import com.blog.kiwi.domain.service.PopularSearchKeywordService
import com.blog.kiwi.domain.spec.PopularKeywordResponseSpec
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SearchStatisticController(
    private val popularSearchKeywordService: PopularSearchKeywordService
) {

    @GetMapping(POPULAR_SEARCH_KEYWORDS)
    fun findAllPopularSearchKeywords(): CommonResponse<List<PopularKeywordResponseSpec>> {
        val result = popularSearchKeywordService.findAllTopNPopularKeywords()
        return CommonResponse.success(result)
    }
}