package com.leoarmelin.meumercado.models

import com.google.gson.annotations.SerializedName

data class Store(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("cnpj")
    val cnpj: String,
    @SerializedName("address")
    val address: String,
)
