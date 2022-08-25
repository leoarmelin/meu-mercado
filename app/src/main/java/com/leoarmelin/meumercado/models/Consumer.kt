package com.leoarmelin.meumercado.models

import com.google.gson.annotations.SerializedName

data class Consumer(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("cpf")
    val cpf: String,
)
