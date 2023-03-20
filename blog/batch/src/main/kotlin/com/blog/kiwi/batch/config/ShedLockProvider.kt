package com.blog.kiwi.batch.config

import com.blog.kiwi.batch.properties.ShedLockProperties
import net.javacrumbs.shedlock.core.LockProvider
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider.Configuration.builder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import java.util.*
import javax.sql.DataSource

@Configuration
class ShedLockProvider(
    private val shedLockProperties: ShedLockProperties,
) {
    @Bean
    fun lockProvider(dataSource: DataSource): LockProvider {
        return JdbcTemplateLockProvider(
            builder()
                .withTableName(shedLockProperties.tableName)
                .withJdbcTemplate(JdbcTemplate(dataSource))
                .withTimeZone(TimeZone.getTimeZone("UTC"))
                .build()
        )
    }
}