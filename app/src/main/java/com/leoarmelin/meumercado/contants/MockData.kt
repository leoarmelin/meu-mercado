package com.leoarmelin.meumercado.contants

import com.leoarmelin.sharedmodels.Unity

object MockData {
    val store = com.leoarmelin.sharedmodels.Store(
        name = "Cool store",
        address = "Cool street"
    )

    val productOne = com.leoarmelin.sharedmodels.Product(
        id = "p-1",
        name = "Product One",
        unity = Unity.UN,
        amount = 3.0,
        unityPrice = 4.98,
        totalPrice = 14.94
    )

    val productTwo = com.leoarmelin.sharedmodels.Product(
        id = "p-2",
        name = "Product Two",
        unity = Unity.UN,
        amount = 1.0,
        unityPrice = 11.28,
        totalPrice = 11.28
    )

    val productThree = com.leoarmelin.sharedmodels.Product(
        id = "p-3",
        name = "Product One",
        unity = Unity.UN,
        amount = 5.0,
        unityPrice = 1.10,
        totalPrice = 5.50
    )

    val ticket = com.leoarmelin.sharedmodels.Ticket(
        id = "t-1",
        priceTotal = 22.0,
        url = "",
        issueAt = "2023-01-01T13:14:15Z",
        products = listOf(
            productOne,
            productTwo,
            productThree
        ),
        store = this.store,
    )
}