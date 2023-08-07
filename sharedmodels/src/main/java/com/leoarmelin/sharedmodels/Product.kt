package com.leoarmelin.sharedmodels

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Product(
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "unity")
    val unity: Unity,
    @Json(name = "amount")
    val amount: Double,
    @Json(name = "unityPrice")
    val unityPrice: Double,
    @Json(name = "totalPrice")
    val totalPrice: Double,
)
