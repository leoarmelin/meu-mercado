package com.leoarmelin.meumercado.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "ticket")
data class Ticket(
    @PrimaryKey
    val key: String,
    @SerializedName("store")
    val store: String,
    @SerializedName("cnpj")
    val cnpj: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("price_total")
    val price_total: Double,
    @SerializedName("payment_method")
    val payment_method: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("customer_document")
    val customer_document: String? = null,
    @SerializedName("customer_name")
    val customer_name: String? = null,
    @SerializedName("items")
    val items: List<Product> = listOf()
)

