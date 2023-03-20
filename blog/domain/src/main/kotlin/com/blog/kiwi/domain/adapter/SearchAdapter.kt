package com.blog.kiwi.domain.adapter

import com.blog.kiwi.core.constant.SortCondition
import com.blog.kiwi.domain.spec.SearchBlogResponseSpec

interface SearchAdapter {

    fun searchBlog(query: String, sort: SortCondition, page :Int, size: Int): SearchBlogResponseSpec
}