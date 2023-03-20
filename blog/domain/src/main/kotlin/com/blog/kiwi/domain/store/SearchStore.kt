package com.blog.kiwi.domain.store

import com.blog.kiwi.domain.entity.PopularSearchKeyword

interface SearchStore {

    fun savePopularSearchKeyword(popularSearchKeywords: List<PopularSearchKeyword>)
}