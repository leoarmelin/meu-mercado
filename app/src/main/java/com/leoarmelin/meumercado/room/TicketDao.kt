package com.leoarmelin.meumercado.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.leoarmelin.meumercado.models.Ticket
import kotlinx.coroutines.flow.Flow

@Dao
interface TicketDao {
    @Query("SELECT * FROM ticket")
    fun fetchAllTickets(): Flow<List<Ticket>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTicket(ticket: Ticket)

    @Query("DELETE FROM ticket where `id` = :id")
    suspend fun deleteTicketById(id: String)

    @Query("DELETE FROM ticket")
    suspend fun deleteAllTickets()
}