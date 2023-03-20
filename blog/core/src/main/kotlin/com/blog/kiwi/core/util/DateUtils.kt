package com.blog.kiwi.core.util

import java.time.LocalDateTime
import java.time.ZoneId

fun getCurrentTimestamp() = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()