package com.blog.kiwi.batch.scheduler

import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.JobParametersBuilder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

abstract class AbstractJobScheduler {
    protected open fun getJobParameters(): JobParameters {
        val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm:ss")
        return JobParametersBuilder()
            .addString("requestAt", LocalDateTime.now().format(dateTimeFormatter))
            .toJobParameters()
    }
}