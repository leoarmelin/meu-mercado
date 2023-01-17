package com.leoarmelin.meumercado.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Product(
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "code")
    val code: String,
    @Json(name = "unity")
    val unity: String,
    @Json(name = "amount")
    val amount: Double,
    @Json(name = "storeId")
    val storeId: String,
    @Json(name = "price")
    val price: Double,
)
