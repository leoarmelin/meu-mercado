package com.leoarmelin.meumercado.models

data class Ticket(
    val store: String,
    val cnpj: String,
    val address: String,
    val quantity: Int,
    val price_total: Double,
    val payment_method: String,
    val date: String,
    val key: String,
    val customer_document: String,
    val customer_name: String,
    val items: List<Product>
)
