package com.leoarmelin.meumercado.extensions

fun Double.toMoney(): String {
    val doubleString = this.toString()
    val beforeDot = doubleString.substringBefore(".")
    var afterDot = doubleString.substringAfter(".").take(2)
    when {
        afterDot.isEmpty() -> afterDot = "00"
        afterDot.length == 1 -> afterDot = "${afterDot}0"
    }
    return "R$${beforeDot},${afterDot}"
}

fun Double.toKg(): String {
    val doubleString = this.toString()
    val beforeDot = doubleString.substringBefore(".")
    val afterDot = doubleString.substringAfter(".")
    return "${beforeDot},${afterDot}kg"
}