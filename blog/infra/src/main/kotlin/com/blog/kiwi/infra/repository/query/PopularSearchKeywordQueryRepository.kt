package com.blog.kiwi.infra.repository.query

import com.blog.kiwi.domain.entity.PopularSearchKeyword
import com.blog.kiwi.domain.spec.PopularKeywordResponseSpec
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface PopularSearchKeywordQueryRepository: JpaRepository<PopularSearchKeyword, Long> {
    @Query(
        value = "SELECT tpsk.keyword as keyword, count(tpsk.keyword) as searchCount " +
            "FROM TBL_POPULAR_SEARCH_KEYWORD tpsk " +
            "GROUP BY tpsk.keyword " +
            "ORDER BY searchCount DESC " +
            "LIMIT :limit",
        nativeQuery = true
    )
    fun findAllTopNPopularKeywords(@Param("limit") limit: Int): List<PopularKeywordResponseSpec>
}