package com.blog.kiwi.api.dto

import com.blog.kiwi.domain.spec.PageSpec
import org.springframework.data.domain.Page

class PageResponse<T> {
    /**
     * 전체 아이템 수
     */
    var total: Int = 0

    /**
     * 다음 페이지 존재 여부
     */
    var hasNext: Boolean = false

    /**
     * 아이템 리스트
     */
    var items: List<T> = listOf()

    companion object {
        fun <T> of(paged: Page<*>, items: List<T>): PageResponse<T> {
            return PageResponse<T>().apply {
                total = paged.totalElements.toInt()
                hasNext = (paged.pageable.pageNumber + 1).toLong() * paged.pageable.pageSize < total
                this.items = items
            }
        }

        fun <T> of(pageSpec: PageSpec, items: List<T>): PageResponse<T> {
            return PageResponse<T>().apply {
                this.total = pageSpec.pageableCount
                this.hasNext = pageSpec.hasNext
                this.items = items
            }
        }
    }
}