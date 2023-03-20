package com.blog.kiwi.infra

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@ConfigurationPropertiesScan("com.blog.kiwi.infra.properties")
@EntityScan(value = ["com.blog.kiwi.*"])
@EnableJpaRepositories(value = ["com.blog.kiwi.*"])
class InfraEntry