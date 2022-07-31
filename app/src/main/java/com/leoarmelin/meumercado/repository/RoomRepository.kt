package com.leoarmelin.meumercado.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.leoarmelin.meumercado.models.Ticket
import com.leoarmelin.meumercado.room.AppDatabase
import com.leoarmelin.meumercado.room.TicketDao

class RoomRepository(application: Application) {
    private var ticketDao: TicketDao

    init {
        val database = AppDatabase.getDatabase(application)
        ticketDao = database.ticketDao()
    }

    val readAllTickets: LiveData<List<Ticket>> = ticketDao.fetchAllTickets()

    suspend fun insertTicket(ticket: Ticket) {
        ticketDao.insertTicket(ticket)
    }

    suspend fun deleteTicketByKey(key: Int) {
        ticketDao.deleteTicketByKey(key)
    }

    suspend fun deleteAllTickets() {
        ticketDao.deleteAllTickets()
    }
}