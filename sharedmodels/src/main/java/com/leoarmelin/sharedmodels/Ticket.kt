package com.leoarmelin.sharedmodels

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Ticket(
    @Json(name = "id")
    val id: String,
    @Json(name = "priceTotal")
    val priceTotal: Double,
    @Json(name = "url")
    val url: String,
    @Json(name = "issueAt")
    val issueAt: String,
    @Json(name = "products")
    val products: List<Product>,
    @Json(name = "store")
    val store: Store,
)

