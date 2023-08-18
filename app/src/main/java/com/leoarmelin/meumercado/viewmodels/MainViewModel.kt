package com.leoarmelin.meumercado.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leoarmelin.meumercado.useCases.GetNfceUseCase
import com.leoarmelin.sharedmodels.Ticket
import com.leoarmelin.sharedmodels.api.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sharedViewModel: SharedViewModel,
    private val getNfceUseCase: GetNfceUseCase,
) : ViewModel() {

    private val _isCameraPermissionDialogOpen = MutableStateFlow(false)
    val isCameraPermissionDialogOpen get() = _isCameraPermissionDialogOpen.asStateFlow()

    private val _isCameraPermissionGranted = MutableStateFlow(false)
    val isCameraPermissionGranted get() = _isCameraPermissionGranted.asStateFlow()

    val isDatePickerOpen = sharedViewModel.isDatePickerOpen
    val selectedDate = sharedViewModel.dateInterval.asStateFlow()

    private val _ticketResult = MutableStateFlow<Result<Ticket>?>(null)
    val ticketResult get() = _ticketResult.asStateFlow()

    fun selectDate(month: Int, year: Int) {
        sharedViewModel.selectDate(month, year)
    }

    fun toggleDatePicker(state: Boolean) {
        sharedViewModel.toggleDatePicker(state)
    }

    fun setCameraPermissionState(state: Boolean) {
        _isCameraPermissionGranted.value = state
    }

    fun getNfce(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getNfceUseCase.execute(url).collect {
                _ticketResult.value = it
            }
        }
    }

    fun togglePermissionDialog(state: Boolean) {
        _isCameraPermissionDialogOpen.value = state
    }

    fun clearTicketResult() {
        _ticketResult.value = null
    }
}