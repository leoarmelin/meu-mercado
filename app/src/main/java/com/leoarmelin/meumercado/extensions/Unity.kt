package com.leoarmelin.meumercado.extensions

import com.leoarmelin.sharedmodels.Unity

val Unity.stringValue get(): String = when (this) {
    Unity.UN -> "un."
    Unity.KG -> "kg"
    Unity.DZ -> "dz."
}

val Unity.toggled get(): Unity = when (this) {
    Unity.UN -> Unity.KG
    Unity.KG -> Unity.DZ
    Unity.DZ -> Unity.UN
}