package com.leoarmelin.meumercado.extensions

import com.leoarmelin.meumercado.models.Product

fun Product.unityAmount(): String {
    return if (this.unity == "UN") "${this.amount.toInt()} un." else this.amount.toKg()
}