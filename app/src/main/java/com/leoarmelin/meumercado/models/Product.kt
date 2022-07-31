package com.leoarmelin.meumercado.models

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id")
    val id: Long,
    @SerializedName("item")
    val item: String,
    @SerializedName("unity")
    val unity: String,
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("unity_price")
    val unity_price: Double,
    @SerializedName("price")
    val price: Double,
)
