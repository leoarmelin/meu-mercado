package com.leoarmelin.meumercado.extensions

import java.time.LocalDateTime

// 2022-07-23 15:33:44

fun String.getLocalDateTime(): LocalDateTime {
    val date: String
    val time: String
    with(this.split(" ")) {
        date = this[0]
        time = this[1]
    }
    val year: Int
    val month: Int
    val day: Int
    with(date.split("-")) {
        year = this[0].toInt()
        month = this[1].toInt()
        day = this[2].toInt()
    }
    val hour: Int
    val minute: Int
    val second: Int
    with(time.split(":")) {
        hour = this[0].toInt()
        minute = this[1].toInt()
        second = this[2].toInt()
    }
    return LocalDateTime.of(year, month, day, hour, minute, second)
}