package com.leoarmelin.scrapper.extensions

import com.leoarmelin.sharedmodels.Unity

fun stringToUnityOrElse(value: String, elseCase: () -> Unity): Unity {
    return try {
        Unity.valueOf(value)
    } catch (e: Exception) {
        elseCase()
    }
}