package com.blog.kiwi.core.config.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import java.io.IOException
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class LocalDateTimeSerializer: JsonSerializer<LocalDateTime>() {

    // e.g '2023-03-18T01:35:30.000+09:00'
    private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")

    override fun serialize(
        localDateTime: LocalDateTime,
        jsonGenerator: JsonGenerator,
        serializerProvider: SerializerProvider,
    ) {
        val zonedDateTime = localDateTime.atZone(ZoneId.of("UTC"))
        val str = dateTimeFormatter.format(zonedDateTime)
        jsonGenerator.writeString(str)
    }
}