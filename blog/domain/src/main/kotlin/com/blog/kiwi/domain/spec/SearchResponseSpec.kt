package com.blog.kiwi.domain.spec


open class SearchResponseSpec<T> {
    open var pageSpec: PageSpec = PageSpec()
    open var items: List<T> = mutableListOf()
}

data class PageSpec(
    val totalCount: Int = 0,
    val pageableCount: Int = 0,
    val hasNext: Boolean = false,
)
