package com.leoarmelin.meumercado.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leoarmelin.meumercado.models.Product
import com.leoarmelin.meumercado.models.Ticket
import com.leoarmelin.meumercado.models.api.Result
import com.leoarmelin.meumercado.models.api.ResultState
import com.leoarmelin.meumercado.repository.NfceRepository
import kotlinx.coroutines.launch

class CameraViewModel(
    private val nfceRepository: NfceRepository = NfceRepository()
) : ViewModel() {
    var isPermissionGranted by mutableStateOf(false)
    var ticketResultState by mutableStateOf<ResultState?>(null)
    var ticket by mutableStateOf<Ticket?>(null)

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

    fun setCameraPermissionState(state: Boolean) {
        isPermissionGranted = state
    }

    fun getNfce(url: String) {
        viewModelScope.launch {
            ticketResultState = ResultState.Loading

            when (val result = nfceRepository.getNfce(url)) {
                is Result.Loading -> {}
                is Result.Success -> {
                    ticket = result.data
                    Log.d("Aoba", "${result.data}")
                    ticketResultState = ResultState.Success
                }
                is Result.Error -> {
                    ticketResultState = ResultState.Error(result.exception)
                }
            }
        }
    }
}