package com.leoarmelin.meumercado.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leoarmelin.sharedmodels.Ticket
import com.leoarmelin.sharedmodels.api.CreateNfceRequest
import com.leoarmelin.sharedmodels.api.Result
import com.leoarmelin.sharedmodels.api.ResultState
import com.leoarmelin.meumercado.repository.ScrapperRepository
import com.leoarmelin.meumercado.repository.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val scrapperRepository: ScrapperRepository,
    private val roomRepository: RoomRepository
) : ViewModel() {

    private val _isCameraPermissionDialogOpen = MutableStateFlow(false)
    val isCameraPermissionDialogOpen get() = _isCameraPermissionDialogOpen.asStateFlow()

    private val _isCameraPermissionGranted = MutableStateFlow(false)
    val isCameraPermissionGranted get() = _isCameraPermissionGranted.asStateFlow()

    private val _selectedTicket = MutableStateFlow<Ticket?>(null)
    val selectedTicket get() = _selectedTicket.asStateFlow()

    private val _ticketsList = MutableStateFlow(emptyList<Ticket>())
    val ticketsList get() = _ticketsList.asStateFlow()

    fun setCameraPermissionState(state: Boolean) {
        _isCameraPermissionGranted.value = state
    }

    private val _getNfceState = MutableStateFlow<ResultState?>(null)
    val getNfceState get() = _getNfceState.asStateFlow()

    fun getNfce(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _getNfceState.value = ResultState.Loading

            scrapperRepository.getNfce(CreateNfceRequest(url)).collectLatest { result ->
                when (result) {
                    is Result.Loading -> {}
                    is Result.Success -> {
                        _selectedTicket.value = result.data
                        insertTicket(result.data)
                        _getNfceState.value = ResultState.Success
                    }
                    is Result.Error -> {
                        _getNfceState.value = ResultState.Error(result.exception)
                    }
                }
            }
        }
    }

    fun fetchAllTickets() {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.readAllTickets.collect {
                _ticketsList.value = it
            }
        }
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
        _isCameraPermissionDialogOpen.value = state
    }

    fun setTicket(ticket: Ticket) {
        _selectedTicket.value = ticket
    }

    fun clearTicketResultState() {
        _getNfceState.value = null
    }
}