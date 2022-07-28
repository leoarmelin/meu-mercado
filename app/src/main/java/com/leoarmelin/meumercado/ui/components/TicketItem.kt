package com.leoarmelin.meumercado.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.leoarmelin.meumercado.R
import com.leoarmelin.meumercado.extensions.getLocalDateTime
import com.leoarmelin.meumercado.extensions.noRippleClickable
import com.leoarmelin.meumercado.extensions.toMoney
import com.leoarmelin.meumercado.models.Ticket
import com.leoarmelin.meumercado.ui.theme.Gray900
import com.leoarmelin.meumercado.ui.theme.Primary500
import com.leoarmelin.meumercado.ui.theme.Secondary800
import java.time.format.DateTimeFormatter

@Composable
fun TicketItem(ticket: Ticket, onItemClick: (ticket: Ticket) -> Unit) {
    Row(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
            .noRippleClickable {
                onItemClick(ticket)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_ticket),
            contentDescription = "Ícone de uma nota fiscal",
            tint = Primary500,
            modifier = Modifier
                .padding(end = 20.dp)
                .size(40.dp)
        )

        Column(
            Modifier
                .padding(end = 12.dp)
                .weight(1f)) {
            Text(text = ticket.store, style = MaterialTheme.typography.h5, color = Primary500)
            Text(
                text = ticket.price_total.toMoney(),
                style = MaterialTheme.typography.h5,
                color = Secondary800
            )
            Text(
                text = ticket.date.getLocalDateTime()
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                style = MaterialTheme.typography.body2,
                color = Gray900
            )
        }
    }
}