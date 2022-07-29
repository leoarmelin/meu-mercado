package com.leoarmelin.meumercado.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.leoarmelin.meumercado.models.Ticket

@Composable
fun TicketsList(ticketsList: List<Ticket>, onItemClick: (ticket: Ticket) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = 16.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(ticketsList) { ticket ->
            TicketItem(ticket = ticket, onItemClick)
        }
    }
}