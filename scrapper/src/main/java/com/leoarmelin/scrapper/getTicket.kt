package com.leoarmelin.scrapper

import android.util.Log
import com.leoarmelin.scrapper.constants.AppCssSelectors
import com.leoarmelin.scrapper.extensions.toMoney
import com.leoarmelin.scrapper.extensions.toTicket
import com.leoarmelin.scrapper.models.ScrapperProduct
import com.leoarmelin.scrapper.models.ScrapperStore
import com.leoarmelin.scrapper.models.ScrapperTicket
import com.leoarmelin.sharedmodels.Ticket
import com.leoarmelin.sharedmodels.Unity
import it.skrape.core.fetcher.HttpFetcher
import it.skrape.core.htmlDocument
import it.skrape.extractIt
import it.skrape.selects.html5.div
import it.skrape.selects.html5.span
import it.skrape.selects.html5.table
import it.skrape.selects.html5.td
import it.skrape.selects.html5.tr
import it.skrape.skrape

fun getTicket(websiteUrl: String): Ticket  {
    val scrapperTicket: ScrapperTicket = skrape(HttpFetcher) {
        request {
            url = websiteUrl
        }

        extractIt { results ->
            htmlDocument {
                // Fill products
                val productsElements = table(AppCssSelectors.MAIN_TABLE.stringValue) {
                    tr {
                        findAll { this }
                    }
                }

                results.products = productsElements.map {
                    val scrapperProduct = ScrapperProduct()

                    it.td {
                        span(AppCssSelectors.PRODUCT_NAME.stringValue) {
                            findFirst {
                                scrapperProduct.name = text
                            }
                        }
                        span(AppCssSelectors.PRODUCT_QUANTITY.stringValue) {
                            findFirst {
                                scrapperProduct.quantity = ownText.toMoney()
                            }
                        }
                        span(AppCssSelectors.PRODUCT_UNITY.stringValue) {
                            findFirst {
                                scrapperProduct.unity = Unity.valueOf(ownText)
                            }
                        }
                        span(AppCssSelectors.PRODUCT_UNITY_VALUE.stringValue) {
                            findFirst {
                                scrapperProduct.unityValue = ownText.toMoney()
                            }
                        }
                        span(AppCssSelectors.PRODUCT_TOTAL_VALUE.stringValue) {
                            findFirst {
                                scrapperProduct.totalValue = text.toMoney()
                            }
                        }
                    }

                    return@map scrapperProduct
                }
                // END Fill products

                // Total value && Store
                div(AppCssSelectors.CONTENT.stringValue) {
                    val totalValueElement = div {
                        findFirst(AppCssSelectors.TOTAL_VALUE.stringValue) {
                            span {
                                findByIndex(0)
                            }
                        }
                    }

                    results.total = totalValueElement.text.toMoney()

                    Log.d("Aoba", "${totalValueElement}")

                    div {
                        findSecond {
                            val storeNameElement = div {
                                findByIndex(0)
                            }
                            val storeAddressElement = div {
                                findLast()
                            }

                            results.store = ScrapperStore(
                                name = storeNameElement.text,
                                address = storeAddressElement.text
                            )
                        }
                    }
                }
                // END Total value && Store

                // URL
                results.url = websiteUrl
                // END URL

                // Issue at
                val dateTimeElement = div(AppCssSelectors.INFO.stringValue) {
                    div {
                        findLast()
                    }
                }

                results.issueAt = dateTimeElement.text.split(" ").takeLast(2).joinToString(" ")
                // END Issue at
            }
        }
    }

    return scrapperTicket.toTicket()
}