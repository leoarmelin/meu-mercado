package com.leoarmelin.meumercado.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val nfceScrapperRepository: NfceScrapperRepository,
    private val roomRepository: RoomRepository
) : ViewModel() {

    var isPermissionDialogOpen by mutableStateOf(false)
    var isPermissionGranted by mutableStateOf(false)
    var ticketResultState by mutableStateOf<ResultState?>(null)
    var ticket by mutableStateOf<Ticket?>(null)
    var ticketsList = mutableStateListOf<Ticket>()

    fun setCameraPermissionState(state: Boolean) {
        isPermissionGranted = state
    }

    fun getNfce(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            ticketResultState = ResultState.Loading

            when (val result = nfceScrapperRepository.getNfce(CreateNfceRequest(url))) {
                is Result.Loading -> {}
                is Result.Success -> {
                    ticket = result.data
                    insertTicket(result.data)
                    ticketResultState = ResultState.Success
                }
                is Result.Error -> {
                    ticketResultState = ResultState.Error(result.exception)
                }
            }
        }
    }

    fun fetchAllTickets(): LiveData<List<Ticket>> {
        return roomRepository.readAllTickets
    }

    private suspend fun insertTicket(ticket: Ticket) {
        roomRepository.insertTicket(ticket = ticket)

        if (!ticketsList.contains(ticket)) {
            ticketsList.add(ticket)
        }
    }

    fun deleteTicketById(id: String) {
        viewModelScope.launch {
            roomRepository.deleteTicketById(id)
        }
    }
}