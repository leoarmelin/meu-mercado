package com.leoarmelin.meumercado.ui.screens.home_screen

import androidx.compose.runtime.Composable
import com.leoarmelin.meumercado.models.Ticket
import com.leoarmelin.meumercado.ui.components.TicketsList

@Composable
fun MyTicketsTab(
    ticketsList: List<Ticket>,
    onItemClick: (Ticket) -> Unit
) {
    TicketsList(ticketsList, onItemClick)
}