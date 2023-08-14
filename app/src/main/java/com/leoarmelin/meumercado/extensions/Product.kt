package com.leoarmelin.meumercado.extensions

import com.leoarmelin.sharedmodels.Product
import com.leoarmelin.sharedmodels.Unity

fun Product.unityAmount(): String {
    return if (this.unity == Unity.UN) "${this.amount.toInt()} un." else this.amount.toKg()
}