package com.blog.kiwi.api.controller

import com.blog.kiwi.api.common.ApiDocumentUtils
import com.blog.kiwi.api.dto.PageResponse
import com.blog.kiwi.api.helper.getSearchBlogRequestFieldsSnippet
import com.blog.kiwi.core.constant.SEARCH_BLOG
import com.blog.kiwi.core.constant.SortCondition
import com.blog.kiwi.core.response.CommonResponse
import com.blog.kiwi.domain.spec.PageSpec
import com.blog.kiwi.domain.spec.SearchBlogResponseSpec
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@AutoConfigureRestDocs
@ActiveProfiles("test")
@WebMvcTest(SearchController::class)
class SearchControllerTest @Autowired constructor(
    @MockBean private val searchController: SearchController,
    private val mockMvc: MockMvc,
) {

    @Test
    fun searchBlogTest() {
        // given
        val response = PageResponse.of(
            PageSpec(100, 10, true),
            listOf(SearchBlogResponseSpec.Item(
                title = "tistory",
                contents = "contents",
                url = "https://~~~",
                blogName = "techBlog",
                dateTime = null
            ))
        )
        BDDMockito.given(searchController.searchBlog("kakao", "kakao", SortCondition.ACCURACY, 0, 10))
            .willReturn(CommonResponse.success(response))

        // when
        val resultActions = mockMvc.perform(get(SEARCH_BLOG)
            .param("query", "0")
            .param("provider", "kakao")
            .param("sort", SortCondition.ACCURACY.name)
            .param("page", "0")
            .param("size", "15")
        )

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "searchBlogTest",
                    ApiDocumentUtils.documentRequest,
                    ApiDocumentUtils.documentResponse,
                )
            )
    }
}