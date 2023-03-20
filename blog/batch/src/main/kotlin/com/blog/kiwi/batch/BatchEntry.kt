package com.blog.kiwi.batch

import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.scheduling.annotation.EnableScheduling

@EnableBatchProcessing
@EnableScheduling
@EnableSchedulerLock(defaultLockAtLeastFor = "PT10S", defaultLockAtMostFor = "PT10S")
@ConfigurationPropertiesScan("com.blog.kiwi.batch.properties")
@SpringBootApplication(scanBasePackages = ["com.blog.kiwi"])
class BatchEntry
