package com.leoarmelin.meumercado.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.leoarmelin.meumercado.models.Ticket

@Composable
fun TicketsList(ticketsList: List<Ticket>, onItemClick: (ticket: Ticket) -> Unit) {
    var ticketFilterType by remember { mutableStateOf(TicketFilterType.None) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = 16.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            TicketFilters(ticketFilterType) { newType ->
                ticketFilterType =
                    if (ticketFilterType == newType) TicketFilterType.None else newType
            }
        }

        items(when (ticketFilterType) {
            TicketFilterType.None -> ticketsList
            TicketFilterType.Value -> ticketsList.sortedByDescending { ticket -> ticket.price_total }
            TicketFilterType.Alphabet -> ticketsList.sortedBy { ticket -> ticket.store }
        }) { ticket ->
            TicketItem(ticket = ticket, onItemClick)
        }
    }
}