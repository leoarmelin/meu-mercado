package com.leoarmelin.meumercado.models

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("code")
    val code: String,
    @SerializedName("unity")
    val unity: String,
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("storeId")
    val storeId: String,
    @SerializedName("price")
    val price: Double,
)
