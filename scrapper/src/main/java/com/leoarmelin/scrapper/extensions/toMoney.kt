package com.leoarmelin.scrapper.extensions

fun String.toMoney(): Double = try {
    this.replace(",", ".").toDoubleOrNull() ?: 0.0
} catch (e: Exception) {
    0.0
}