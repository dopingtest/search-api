package com.blog.kiwi.domain.spec

import java.time.LocalDateTime

class SearchBlogResponseSpec: SearchResponseSpec<SearchBlogResponseSpec.Item>() {

    companion object {
        fun defaultInstance() = SearchBlogResponseSpec()
        fun of(
            pageSpec: PageSpec,
            items: List<Item>
        ) = defaultInstance().apply {
                this.pageSpec = pageSpec
                this.items = items
            }
    }

    data class Item(
        val title: String = "",
        val contents: String = "",
        val url: String = "",
        val blogName: String = "",
        val thumbnail: String = "",
        val dateTime: LocalDateTime? = null
    ) {
        companion object {
            fun of(
                title: String = "",
                contents: String = "",
                url: String = "",
                blogName: String = "",
                thumbnail: String = "",
                dateTime: LocalDateTime? = null
            ) = Item(title, contents, url, blogName, thumbnail, dateTime)
        }
    }
}