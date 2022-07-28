package com.leoarmelin.meumercado.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.leoarmelin.meumercado.models.Product
import com.leoarmelin.meumercado.models.Ticket

class MainViewModel : ViewModel() {
    //    val userSavedTickets = mutableStateListOf<Ticket>()
    val userSavedTickets = mutableStateListOf(
        Ticket(
            store = "L GALINA PERFUMARIA  EPP",
            cnpj = "07.186.389/0001-50",
            address = "AV SAO PAULO,743,,ZONA 01,MARINGA,PR",
            quantity = 3,
            price_total = 95.7,
            payment_method = "Cartão de Crédito",
            date = "2022-07-23 15:33:44",
            key = "4122 0707 1863 8900 0150 6500 3000 1110 7412 0006 5836",
            customer_document = "808.548.349-15",
            customer_name = "JANINE",
            items = listOf(
                Product(
                    item = "NSPA COND AMEI 300ml",
                    id = 71132,
                    unity = "UN",
                    amount = 1.0,
                    unity_price = 49.9,
                    price = 49.9
                ),
                Product(
                    item = "NSPA SHAMP AMEI 300 ml",
                    id = 71213,
                    unity = "UN",
                    amount = 1.0,
                    unity_price = 46.9,
                    price = 46.9
                ),
                Product(
                    item = "CAIXA M INST 2021 ROSA ECONOMICA",
                    id = 3529,
                    unity = "UN",
                    amount = 1.0,
                    unity_price = 2.9,
                    price = 2.9
                )
            )
        )
    )
}