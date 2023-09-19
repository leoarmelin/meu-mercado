package com.leoarmelin.sharedmodels

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
enum class Unity {
    KG,
    UN,
}