package com.leoarmelin.sharedmodels.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateNfceRequest(
    @Json(name = "url")
    val url: String
)
