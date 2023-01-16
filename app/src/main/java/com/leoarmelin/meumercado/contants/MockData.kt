package com.leoarmelin.meumercado.contants

import com.leoarmelin.meumercado.models.Product
import com.leoarmelin.meumercado.models.Store
import com.leoarmelin.meumercado.models.Ticket

object MockData {
    val store = Store(
        id = "s-1",
        name = "Cool store",
        cnpj = "1234.5678",
        address = "Cool street"
    )

    val productOne = Product(
        id = "p-1",
        name = "Product One",
        code = "12345",
        unity = "UN",
        amount = 3.0,
        storeId = "s-1",
        price = 4.98,
    )

    val productTwo = Product(
        id = "p-2",
        name = "Product Two",
        code = "123456",
        unity = "UN",
        amount = 1.0,
        storeId = "s-1",
        price = 11.28,
    )

    val productThree = Product(
        id = "p-3",
        name = "Product One",
        code = "1234567",
        unity = "UN",
        amount = 5.0,
        storeId = "s-1",
        price = 1.10,
    )

    val ticketWithoutConsumer = Ticket(
        id = "t-1",
        quantity = 3,
        priceTotal = 22.0,
        key = "1234",
        url = "",
        issueAt = "2023-01-01T13:14:15Z",
        products = listOf(
            productOne,
            productTwo,
            productThree
        ),
        store = this.store,
        consumer = null,
    )
}