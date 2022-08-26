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
import com.leoarmelin.meumercado.extensions.toMoney
import com.leoarmelin.meumercado.extensions.unityAmount
import com.leoarmelin.meumercado.models.Product
import com.leoarmelin.meumercado.ui.theme.Gray900
import com.leoarmelin.meumercado.ui.theme.Secondary800

@Composable
fun ProductItem(product: Product) {
    Row(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = if (product.unity == "UN") R.drawable.ic_egg else R.drawable.ic_balance),
            contentDescription = "Ícone de ${if (product.unity == "UN") "um ovo" else "uma balança"}",
            tint = Secondary800,
            modifier = Modifier
                .padding(end = 20.dp)
                .size(40.dp)
        )

        Column(Modifier.padding(end = 12.dp).weight(1f)) {
            Text(text = product.name ?: product.code, style = MaterialTheme.typography.h5, color = Secondary800)
            Text(
                text = product.unityAmount(),
                style = MaterialTheme.typography.caption,
                color = Gray900
            )
            Text(
                text = "Preço unitário: ${product.price.toMoney()}",
                style = MaterialTheme.typography.caption,
                color = Gray900
            )
        }

        Text(
            text = (product.price * product.amount).toMoney(),
            style = MaterialTheme.typography.h5,
            color = Secondary800,
            modifier = Modifier.align(Alignment.Top)
        )
    }
}