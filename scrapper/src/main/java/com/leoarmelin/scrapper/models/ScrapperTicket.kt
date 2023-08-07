package com.leoarmelin.scrapper.models

internal data class ScrapperTicket(
    var products: List<ScrapperProduct> = emptyList(),
    var total: Double = 0.0,
    var url: String = "",
    var issueAt: String = "",
    var store: ScrapperStore = ScrapperStore()
)