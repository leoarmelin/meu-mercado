package com.leoarmelin.sharedmodels

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Store(
    @Json(name = "name")
    val name: String,
    @Json(name = "address")
    val address: String,
)
