package com.blog.kiwi.batch.tasklet

import com.blog.kiwi.core.util.UrlSeparatorUtils
import com.blog.kiwi.domain.entity.PopularSearchKeyword
import com.blog.kiwi.domain.service.PopularSearchKeywordService
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Service
class PopularSearchKeywordTasklet(
    private val popularSearchKeywordService: PopularSearchKeywordService,
    private val objectMapper: ObjectMapper
): Tasklet {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        log.info("# [PopularSearchKeywordTasklet] - start")

        val logFile = File("./logs/blog.log")

        if (!logFile.exists()) {
            // Handle the case where the log file doesn't exist
            log.error("# [PopularSearchKeywordTasklet] Log file not found: ${logFile.absolutePath}")
            return RepeatStatus.FINISHED
        }

        val fileReader = FileReader(logFile)
        val bufferedReader = BufferedReader(fileReader)
        val popularSearchKeywords = mutableListOf<PopularSearchKeyword>()
        val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")

        // resource and then closes it down correctly whether an exception
        bufferedReader.use { reader ->
            reader.lines().forEach { line ->
                val jsonNode = objectMapper.readTree(line)
                val messageNode = jsonNode.get("message")
                val payloadNode = objectMapper.readTree(messageNode.asText())

                val popularSearchKeyword = PopularSearchKeyword(
                    keyword = UrlSeparatorUtils.getQuery(payloadNode.get("queryString").asText()),
                    userIp = payloadNode.get("clientIp").asText(),
                    createdAt = ZonedDateTime.parse(jsonNode.get("@timestamp").asText(), dateTimeFormatter).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime()
                )
                popularSearchKeywords.add(popularSearchKeyword)
            }
        }

        popularSearchKeywordService.sync(popularSearchKeywords)

        return RepeatStatus.FINISHED
    }
}