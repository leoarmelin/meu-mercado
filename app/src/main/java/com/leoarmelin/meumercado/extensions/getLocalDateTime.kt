package com.leoarmelin.meumercado.extensions

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun String.toZonedDateTime(): ZonedDateTime = LocalDateTime.parse(this, DateTimeFormatter.ISO_DATE_TIME)
    .atZone(ZoneOffset.UTC).withZoneSameInstant(ZoneId.systemDefault())