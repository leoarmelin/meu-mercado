package com.leoarmelin.meumercado.viewmodels

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.leoarmelin.meumercado.models.Product
import com.leoarmelin.meumercado.models.Ticket
import com.leoarmelin.meumercado.models.api.Result
import com.leoarmelin.meumercado.models.api.ResultState
import com.leoarmelin.meumercado.repository.NfceRepository
import com.leoarmelin.meumercado.repository.RoomRepository
import kotlinx.coroutines.launch

class MainViewModel(
    appObj: Application,
    private val nfceRepository: NfceRepository = NfceRepository(),
) : AndroidViewModel(appObj) {
    private val roomRepository: RoomRepository = RoomRepository(appObj)

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

    fun insertTicket(ticket: Ticket) {
        viewModelScope.launch {
            Log.d("Aoba", "Ticket inserting: $ticket")
            roomRepository.insertUser(ticket = ticket)
        }

    }

    fun deleteTicketByKey(key: Int) {
        viewModelScope.launch {
            roomRepository.deleteTicketByKey(key)
        }

    }
}