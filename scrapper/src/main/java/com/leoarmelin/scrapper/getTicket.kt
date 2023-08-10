package com.leoarmelin.scrapper

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
import java.lang.IndexOutOfBoundsException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun getTicket(websiteUrl: String): Ticket {
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

                    div {
                        findSecond {
                            val storeNameElement = div {
                                findByIndex(0)
                            }
                            val storeAddressElement = div {
                                findLast()
                            }
                            var address = storeAddressElement.text
                            try {
                                address = storeAddressElement.text.split(",")
                                    .filter { it.isNotEmpty() && it != " " }
                                    .joinToString(", ") { it.trim()}
                            } catch (e: Exception) {
                                println("Address error: $e")
                            }

                            results.store = ScrapperStore(
                                name = storeNameElement.text,
                                address = address
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
                try {
                    val dateTimeArray = dateTimeElement.text.split(" ").takeLast(2)
                    val dateArray =
                        dateTimeArray[0].split("/").map { it.toInt() }
                    val timeArray =
                        dateTimeArray[1].split(":").map { it.toIntOrNull() ?: 0 }
                    val date = LocalDateTime.of(
                        dateArray[2],
                        dateArray[1],
                        dateArray[0],
                        timeArray[0],
                        timeArray[1],
                        timeArray[2]
                    )

                    results.issueAt = date.format(DateTimeFormatter.ISO_DATE_TIME)
                } catch (e: IndexOutOfBoundsException) {
                    results.issueAt = ""
                } catch (e: NumberFormatException) {
                    results.issueAt = ""
                }
                // END Issue at
            }
        }
    }

    return scrapperTicket.toTicket()
}