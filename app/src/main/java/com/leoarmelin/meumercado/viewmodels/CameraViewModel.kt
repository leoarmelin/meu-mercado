package com.leoarmelin.meumercado.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
                    ticketResultState = ResultState.Success
                }
                is Result.Error -> {
                    ticketResultState = ResultState.Error(result.exception)
                }
            }
        }
    }
}