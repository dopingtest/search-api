package com.blog.kiwi.batch.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "shedlock")
class ShedLockProperties(
    val tableName: String
)