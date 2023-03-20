package com.blog.kiwi.core.config

import com.blog.kiwi.core.config.serializer.LocalDateTimeDeserializer
import com.blog.kiwi.core.config.serializer.LocalDateTimeSerializer
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.module.SimpleModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.LocalTime

@Configuration
class ObjectMapperConfig {

    @Bean
    fun objectMapper(): ObjectMapper {
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        val om = ObjectMapper()

        val simpleModule = SimpleModule()

        simpleModule.addSerializer(LocalDateTime::class.java, LocalDateTimeSerializer())
        simpleModule.addDeserializer(LocalDateTime::class.java, LocalDateTimeDeserializer())

        om.registerModule(simpleModule)
        om.setSerializationInclusion(JsonInclude.Include.NON_NULL) // exclude properties has null values
        om.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true) // 작은 따옴표 허용
        om.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true) // 따옴표 없는 필드 허용
        om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        om.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
        om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false) // Disables the exception thrown for empty beans with no properties
        om.configure(SerializationFeature.INDENT_OUTPUT, true) // pretty print
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false) // 알 수 없는 속성에 대한 예외 비활성화
        om.dateFormat = dateFormat

        return om
    }
}