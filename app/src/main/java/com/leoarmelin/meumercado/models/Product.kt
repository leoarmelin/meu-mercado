package com.leoarmelin.meumercado.models

data class Product(
    val id: Int,
    val item: String,
    val unity: String,
    val amount: Double,
    val unity_price: Double,
    val price: Double,
)
