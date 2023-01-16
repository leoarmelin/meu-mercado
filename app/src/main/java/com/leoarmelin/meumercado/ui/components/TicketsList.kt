package com.leoarmelin.meumercado.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.leoarmelin.meumercado.contants.MockData
import com.leoarmelin.meumercado.models.Ticket
import com.leoarmelin.meumercado.ui.theme.MeuMercadoTheme

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
            TicketFilterType.Value -> ticketsList.sortedByDescending { ticket -> ticket.priceTotal }
            TicketFilterType.Alphabet -> ticketsList.sortedBy { ticket -> ticket.store.name }
        }) { ticket ->
            TicketItem(ticket = ticket, onItemClick)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OneItem() {
    MeuMercadoTheme {
        TicketsList(ticketsList = listOf(MockData.ticketWithoutConsumer), onItemClick = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun Empty() {
    MeuMercadoTheme {
        TicketsList(ticketsList = emptyList(), onItemClick = {})
    }
}