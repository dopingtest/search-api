package com.blog.kiwi.batch.scheduler

import com.blog.kiwi.batch.job.PopularSearchKeywordJobConfig
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock
import org.apache.commons.lang3.exception.ExceptionUtils
import org.slf4j.LoggerFactory
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class PopularSearchKeywordJobScheduler(
    private val jobConfig: PopularSearchKeywordJobConfig,
    private val jobLauncher: JobLauncher,
): AbstractJobScheduler() {

    private val log = LoggerFactory.getLogger(javaClass)

    // 1분 마다 인기 검색어 Job 수행
    @Scheduled(cron = "1 * * * * *", zone = "Asia/Seoul")
    @SchedulerLock(name = "popularSearchKeywordJob", lockAtLeastFor = "PT59S", lockAtMostFor = "PT59S")
    fun runJob() {
        try {
            jobLauncher.run(jobConfig.popularSearchKeywordJob(), getJobParameters())
        } catch (e: Exception) {
            log.error("# [PopularSearchKeywordJobScheduler.popularSearchKeywordJob] : {}", ExceptionUtils.getStackTrace(e))
        }
    }
}