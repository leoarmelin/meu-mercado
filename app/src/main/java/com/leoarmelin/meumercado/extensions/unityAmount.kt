package com.leoarmelin.meumercado.extensions

import com.leoarmelin.meumercado.models.Product

fun Product.unityAmount(): String {
    return if (this.unity == "unidade") "${this.amount.toInt()} un." else this.amount.toKg()
}