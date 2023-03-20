package com.blog.kiwi.core.config.serializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeDeserializer: JsonDeserializer<LocalDateTime>() {

    // e.g '2023-03-18T01:35:30.000+09:00'
    private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): LocalDateTime {
        return ZonedDateTime.parse(p.valueAsString, dateTimeFormatter).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime()
    }
}