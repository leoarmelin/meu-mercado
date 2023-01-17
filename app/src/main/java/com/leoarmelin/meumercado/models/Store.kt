package com.leoarmelin.meumercado.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Store(
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "cnpj")
    val cnpj: String,
    @Json(name = "address")
    val address: String,
)
