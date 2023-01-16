package com.leoarmelin.meumercado.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leoarmelin.meumercado.models.Ticket
import com.leoarmelin.meumercado.models.api.CreateNfceRequest
import com.leoarmelin.meumercado.models.api.Result
import com.leoarmelin.meumercado.models.api.ResultState
import com.leoarmelin.meumercado.repository.NfceScrapperRepository
import com.leoarmelin.meumercado.repository.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val nfceScrapperRepository: NfceScrapperRepository,
    private val roomRepository: RoomRepository
) : ViewModel() {

    private val _isPermissionDialogOpen = MutableStateFlow(false)
    val isPermissionDialogOpen get() = _isPermissionDialogOpen.asStateFlow()

    private val _isPermissionGranted = MutableStateFlow(false)
    val isPermissionGranted get() = _isPermissionGranted.asStateFlow()

    private val _ticketResultState = MutableStateFlow<ResultState?>(null)
    val ticketResultState get() = _ticketResultState.asStateFlow()

    private val _ticket = MutableStateFlow<Ticket?>(null)
    val ticket get() = _ticket.asStateFlow()

    private val _ticketsList = MutableStateFlow(emptyList<Ticket>())
    val ticketsList get() = _ticketsList.asStateFlow()

    fun setCameraPermissionState(state: Boolean) {
        _isPermissionGranted.value = state
    }

    fun getNfce(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _ticketResultState.value = ResultState.Loading

            when (val result = nfceScrapperRepository.getNfce(CreateNfceRequest(url))) {
                is Result.Loading -> {}
                is Result.Success -> {
                    _ticket.value = result.data
                    insertTicket(result.data)
                    _ticketResultState.value = ResultState.Success
                }
                is Result.Error -> {
                    _ticketResultState.value = ResultState.Error(result.exception)
                }
            }
        }
    }

    fun fetchAllTickets(): LiveData<List<Ticket>> {
        return roomRepository.readAllTickets
    }

    private suspend fun insertTicket(ticket: Ticket) {
        roomRepository.insertTicket(ticket = ticket)

        if (!ticketsList.value.contains(ticket)) {
            val newList = ticketsList.value.toMutableList().also {
                it.add(ticket)
            }
            _ticketsList.value = newList
        }
    }

    fun deleteTicketById(id: String) {
        viewModelScope.launch {
            roomRepository.deleteTicketById(id)
        }
    }

    fun togglePermissionDialog(state: Boolean) {
        _isPermissionDialogOpen.value = state
    }

    fun setTicket(ticket: Ticket) {
        _ticket.value = ticket
    }

    fun clearTicketResultState() {
        _ticketResultState.value = null
    }
}