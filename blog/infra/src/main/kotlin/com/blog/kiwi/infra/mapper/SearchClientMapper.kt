package com.blog.kiwi.infra.mapper

import com.blog.kiwi.core.annotation.Mapper
import com.blog.kiwi.domain.spec.PageSpec
import com.blog.kiwi.domain.spec.SearchBlogResponseSpec
import com.blog.kiwi.infra.dto.KakaoSearchBlogResponse

@Mapper
class SearchClientMapper {

    fun toSpec(dto: KakaoSearchBlogResponse?): SearchBlogResponseSpec {
        val spec = dto?.let {
            val pageSpec = PageSpec(it.meta.totalCount, it.meta.pageableCount, !it.meta.isEnd)
            val items = it.documents.asSequence().map {
                blogDocument -> SearchBlogResponseSpec.Item.of(
                    title = blogDocument.title,
                    contents = blogDocument.contents,
                    url = blogDocument.url,
                    blogName = blogDocument.blogName,
                    thumbnail = blogDocument.thumbnail,
                    dateTime = blogDocument.dateTime
                )
            }.toList()
            SearchBlogResponseSpec.of(pageSpec, items)
        } ?: SearchBlogResponseSpec.defaultInstance()

        return spec
    }
}