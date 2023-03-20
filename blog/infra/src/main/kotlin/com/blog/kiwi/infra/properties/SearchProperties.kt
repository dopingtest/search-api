package com.blog.kiwi.infra.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "search")
class SearchProperties(
    val kakao: KakaoProperties,
    val naver: NaverProperties
)

data class KakaoProperties(
    val endpoint: String,
    val apiKey: String
)

data class NaverProperties(
    val endpoint: String,
    val apiKey: String
)