package com.leoarmelin.meumercado.extensions

import com.leoarmelin.sharedmodels.Product
import com.leoarmelin.sharedmodels.Unity

fun Product.unityAmount() = when(this.unity) {
    Unity.UN, Unity.DZ -> "${this.amount.toInt()} ${this.unity.stringValue}"
    Unity.KG -> this.amount.toKg()
}