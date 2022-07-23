package com.leoarmelin.meumercado.models

data class Product(
    val item: String,
    val id: Int,
    val unity: String,
    val amount: Double,
    val unity_price: Double,
    val price: Double,
)
