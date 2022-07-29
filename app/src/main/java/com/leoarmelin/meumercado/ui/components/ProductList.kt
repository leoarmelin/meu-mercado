package com.leoarmelin.meumercado.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.leoarmelin.meumercado.models.Ticket
import com.leoarmelin.meumercado.ui.theme.Gray400
import com.leoarmelin.meumercado.ui.theme.Secondary50

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
                text = ticket.store,
                style = MaterialTheme.typography.h3,
                color = Gray400,
                textAlign = TextAlign.Center
            )

            Text(
                text = ticket.address,
                style = MaterialTheme.typography.body2,
                color = Gray400,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        items(ticket.items) { product ->
            ProductItem(product)
        }
    }
}