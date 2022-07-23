package com.leoarmelin.meumercado.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leoarmelin.meumercado.models.Ticket
import com.leoarmelin.meumercado.models.api.Result
import com.leoarmelin.meumercado.repository.NfceRepository
import kotlinx.coroutines.launch

class CameraViewModel(
    private val nfceRepository: NfceRepository = NfceRepository()
) : ViewModel() {
    var isPermissionGranted by mutableStateOf(false)
    var ticket by mutableStateOf<Result<Ticket>?>(null)

    fun setCameraPermissionState(state: Boolean) {
        isPermissionGranted = state
    }

    fun getNfce(url: String) {
        viewModelScope.launch {
            ticket = nfceRepository.getNfce(url)
        }
    }
}