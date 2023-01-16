package com.leoarmelin.meumercado.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.leoarmelin.meumercado.R

@Composable
fun TicketFilters(ticketFilterType: TicketFilterType, onClick: (TicketFilterType) -> Unit) {
    val valueArrowRotation by animateFloatAsState(targetValue = if (ticketFilterType == TicketFilterType.Value) 180f else 0f)
    val alphabetArrowRotation by animateFloatAsState(targetValue = if (ticketFilterType == TicketFilterType.Alphabet) 180f else 0f)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.End)
    ) {
        FilterCard(
            ticketFilterType == TicketFilterType.Value,
            onClick = { onClick(TicketFilterType.Value) }
        ) {
            Text(text = "$", style = MaterialTheme.typography.body2, color = Color.White)
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_up),
                contentDescription = "Seta apontando veticalmente",
                tint = Color.White,
                modifier = Modifier
                    .padding(start = 1.dp)
                    .size(12.dp)
                    .graphicsLayer {
                        rotationX = valueArrowRotation
                    },
            )
        }

        FilterCard(
            ticketFilterType == TicketFilterType.Alphabet,
            onClick = { onClick(TicketFilterType.Alphabet) }
        ) {
            Text(text = "A", style = MaterialTheme.typography.body2, color = Color.White)
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_up),
                contentDescription = "Seta apontando horizontalmente",
                tint = Color.White,
                modifier = Modifier
                    .padding(horizontal = 2.dp)
                    .size(12.dp)
                    .graphicsLayer {
                        rotationY = alphabetArrowRotation
                        rotationZ = 90f
                    },
            )
            Text(text = "Z", style = MaterialTheme.typography.body2, color = Color.White)
        }
    }
}

enum class TicketFilterType {
    Alphabet,
    Value,
    None,
}