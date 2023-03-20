package com.blog.kiwi.domain.spec

interface PopularKeywordResponseSpec {
    fun getKeyword(): String
    fun getSearchCount(): Int
}