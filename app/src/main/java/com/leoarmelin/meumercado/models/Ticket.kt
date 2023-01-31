package com.leoarmelin.meumercado.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "ticket")
@JsonClass(generateAdapter = true)
data class Ticket(
    @PrimaryKey
    @Json(name = "id")
    val id: String,
    @Json(name = "quantity")
    val quantity: Int,
    @Json(name = "priceTotal")
    val priceTotal: Double,
    @Json(name = "key")
    val key: String,
    @Json(name = "url")
    val url: String,
    @Json(name = "issueAt")
    val issueAt: String,
    @Json(name = "products")
    val products: List<Product>,
    @Json(name = "store")
    val store: Store,
    @Json(name = "consumer")
    val consumer: Consumer? = null,
)

