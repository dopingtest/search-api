package com.blog.kiwi.api.helper

import com.blog.kiwi.api.common.ApiDocumentUtils
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.payload.RequestFieldsSnippet

fun getSearchBlogRequestFieldsSnippet(): RequestFieldsSnippet {
    return PayloadDocumentation.requestFields(
        PayloadDocumentation.fieldWithPath("query")
            .attributes(ApiDocumentUtils.required(), ApiDocumentUtils.defaultValue("kakao"))
            .description("검색 키워드"),
        PayloadDocumentation.fieldWithPath("provider")
            .attributes(ApiDocumentUtils.required(), ApiDocumentUtils.defaultValue("kakao"))
            .description("검색 제공자 (kakao, naver)"),
        PayloadDocumentation.fieldWithPath("sort")
            .attributes(ApiDocumentUtils.required(), ApiDocumentUtils.defaultValue("accuracy"))
            .description("정렬(accuracy, recency)"),
        PayloadDocumentation.fieldWithPath("page")
            .attributes(ApiDocumentUtils.required(), ApiDocumentUtils.defaultValue(0))
            .description("페이지"),
        PayloadDocumentation.fieldWithPath("size")
            .attributes(ApiDocumentUtils.required(), ApiDocumentUtils.defaultValue(10))
            .description("한 페이지에 보여줄 크기"),
    )
}