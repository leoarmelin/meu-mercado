package com.leoarmelin.meumercado.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "ticket")
data class Ticket(
    @PrimaryKey
    val id: String,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("priceTotal")
    val priceTotal: Double,
    @SerializedName("key")
    val key: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("issueAt")
    val issueAt: String,
    @SerializedName("products")
    val products: List<Product>,
    @SerializedName("store")
    val store: Store,
    @SerializedName("consumer")
    val consumer: Consumer? = null,
)

