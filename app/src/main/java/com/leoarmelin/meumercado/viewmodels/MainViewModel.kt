package com.leoarmelin.meumercado.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.leoarmelin.meumercado.models.Ticket
import com.leoarmelin.meumercado.models.api.Result
import com.leoarmelin.meumercado.models.api.ResultState
import com.leoarmelin.meumercado.repository.NfceRepository
import com.leoarmelin.meumercado.repository.RoomRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    appObj: Application,
    private val nfceRepository: NfceRepository = NfceRepository(),
) : AndroidViewModel(appObj) {
    private val roomRepository: RoomRepository = RoomRepository(appObj)

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

            when (val result = nfceRepository.getNfce(url)) {
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

    fun deleteTicketByKey(key: Int) {
        viewModelScope.launch {
            roomRepository.deleteTicketByKey(key)
        }
    }
}