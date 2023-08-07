package com.leoarmelin.meumercado.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.leoarmelin.meumercado.R
import com.leoarmelin.meumercado.contants.MockData
import com.leoarmelin.meumercado.extensions.noRippleClickable
import com.leoarmelin.meumercado.extensions.toMoney
import com.leoarmelin.sharedmodels.Ticket
import com.leoarmelin.meumercado.ui.theme.Gray900
import com.leoarmelin.meumercado.ui.theme.MeuMercadoTheme
import com.leoarmelin.meumercado.ui.theme.Primary500
import com.leoarmelin.meumercado.ui.theme.Secondary800

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
            contentDescription = stringResource(R.string.icone_de_uma_nota_fiscal),
            tint = Primary500,
            modifier = Modifier
                .padding(end = 20.dp)
                .size(40.dp)
        )

        Column(
            Modifier
                .padding(end = 12.dp)
                .weight(1f)
        ) {
            Text(text = ticket.store.name, style = MaterialTheme.typography.h5, color = Primary500)
            Text(
                text = ticket.priceTotal.toMoney(),
                style = MaterialTheme.typography.h5,
                color = Secondary800
            )
            Text(
                text = ticket.issueAt,
                style = MaterialTheme.typography.body2,
                color = Gray900
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    MeuMercadoTheme {
        TicketItem(ticket = MockData.ticket, onItemClick = {})
    }
}