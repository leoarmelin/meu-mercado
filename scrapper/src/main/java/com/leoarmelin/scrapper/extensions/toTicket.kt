package com.leoarmelin.scrapper.extensions

import com.leoarmelin.scrapper.models.ScrapperProduct
import com.leoarmelin.scrapper.models.ScrapperStore
import com.leoarmelin.scrapper.models.ScrapperTicket
import com.leoarmelin.sharedmodels.Product
import com.leoarmelin.sharedmodels.Store
import com.leoarmelin.sharedmodels.Ticket
import java.util.UUID

internal fun ScrapperTicket.toTicket(): Ticket = Ticket(
    id = UUID.randomUUID().toString(),
    priceTotal = this.total,
    url = this.url,
    issueAt = this.issueAt,
    products = this.products.toProduct(),
    store = this.store.toStore(),
)

internal fun List<ScrapperProduct>.toProduct(): List<Product> = this.map {
    Product(
        id = UUID.randomUUID().toString(),
        name = it.name,
        unity = it.unity,
        amount = it.quantity,
        unityPrice = it.unityValue,
        totalPrice = it.totalValue
    )
}

internal fun ScrapperStore.toStore(): Store = Store(
    name = this.name,
    address = this.address
)