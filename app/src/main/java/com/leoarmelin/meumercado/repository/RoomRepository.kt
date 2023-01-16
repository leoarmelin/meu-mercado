package com.leoarmelin.meumercado.repository

import androidx.lifecycle.LiveData
import com.leoarmelin.meumercado.models.Ticket
import com.leoarmelin.meumercado.room.TicketDao
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val ticketDao: TicketDao
) {
    val readAllTickets: LiveData<List<Ticket>> = ticketDao.fetchAllTickets()

    suspend fun insertTicket(ticket: Ticket) {
        ticketDao.insertTicket(ticket)
    }

    suspend fun deleteTicketById(id: String) {
        ticketDao.deleteTicketById(id)
    }

    suspend fun deleteAllTickets() {
        ticketDao.deleteAllTickets()
    }
}