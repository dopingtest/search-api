package com.blog.kiwi.batch.job

import com.blog.kiwi.batch.tasklet.PopularSearchKeywordTasklet
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PopularSearchKeywordJobConfig(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
    private val popularSearchKeywordTasklet: PopularSearchKeywordTasklet
) {

    @Bean
    fun popularSearchKeywordJob(): Job {
        return jobBuilderFactory.get("popularSearchKeywordJob")
            .incrementer(RunIdIncrementer())
            .start(popularSearchKeywordStep())
            .build()
    }

    @Bean
    @JobScope
    fun popularSearchKeywordStep(): Step {
        return stepBuilderFactory.get("popularSearchKeywordStep")
            .tasklet(popularSearchKeywordTasklet)
            .build()
    }
}