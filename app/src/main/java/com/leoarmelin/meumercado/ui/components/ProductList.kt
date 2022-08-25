package com.leoarmelin.meumercado.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leoarmelin.meumercado.extensions.toZonedDateTime
import com.leoarmelin.meumercado.models.Ticket
import com.leoarmelin.meumercado.ui.theme.Gray400
import com.leoarmelin.meumercado.ui.theme.Metropolis
import com.leoarmelin.meumercado.ui.theme.Secondary50
import java.time.format.DateTimeFormatter

@Composable
fun ProductList(ticket: Ticket) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Secondary50),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(top = 38.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Text(
                text = ticket.store.name,
                style = MaterialTheme.typography.h3,
                color = Gray400,
                textAlign = TextAlign.Center,
            )

            Text(
                text = ticket.store.address,
                style = MaterialTheme.typography.body2,
                color = Gray400,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        items(ticket.products) { product ->
            ProductItem(product)
        }

        item {
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Quantidade total: ")
                    }
                    append(if (ticket.quantity == 1) "1 item" else "${ticket.quantity} itens")
                },
                fontFamily = Metropolis,
                fontSize = 16.sp,
                color = Gray400,
                modifier = Modifier
                    .padding(top = 8.dp, start = 24.dp, end = 24.dp)
                    .fillMaxWidth()
            )

//            Text(
//                text = buildAnnotatedString {
//                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
//                        append("Método: ")
//                    }
//                    append(ticket.payment_method)
//                },
//                fontFamily = Metropolis,
//                fontSize = 16.sp,
//                color = Gray400,
//                modifier = Modifier
//                    .padding(top = 8.dp, start = 24.dp, end = 24.dp)
//                    .fillMaxWidth()
//            )

            Text(
                style = MaterialTheme.typography.h5,
                text = ticket.issueAt.toZonedDateTime()
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                color = Gray400,
                modifier = Modifier
                    .padding(top = 8.dp, start = 24.dp, end = 24.dp)
                    .fillMaxWidth()
            )
        }
    }
}