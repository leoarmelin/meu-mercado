package com.leoarmelin.meumercado.extensions

fun Double.toKg(): String {
    val doubleString = this.toString()
    val beforeDot = doubleString.substringBefore(".")
    val afterDot = doubleString.substringAfter(".")
    return "${beforeDot},${afterDot}kg"
}