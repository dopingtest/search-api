package com.blog.kiwi.infra.repository.command

import com.blog.kiwi.domain.entity.PopularSearchKeyword
import org.springframework.data.jpa.repository.JpaRepository

interface PopularSearchKeywordCommandRepository: JpaRepository<PopularSearchKeyword, Long> {
}