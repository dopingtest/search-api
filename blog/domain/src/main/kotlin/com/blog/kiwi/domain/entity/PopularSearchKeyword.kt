package com.blog.kiwi.domain.entity

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Index
import javax.persistence.Table

@Entity
@Table(name = "tbl_popular_search_keyword", indexes = [Index(name = "idx_keyword", columnList = "keyword")])
class PopularSearchKeyword(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0L,

    @Column(name = "keyword", nullable = false, updatable = false)
    val keyword: String,

    @Column(name = "user_ip", nullable = false, updatable = false)
    val userIp: String,

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime
)