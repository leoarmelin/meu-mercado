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
import com.leoarmelin.meumercado.extensions.toMoney
import com.leoarmelin.meumercado.extensions.unityAmount
import com.leoarmelin.meumercado.ui.theme.Black
import com.leoarmelin.sharedmodels.Unity

@Composable
fun ProductItem(product: com.leoarmelin.sharedmodels.Product) {
    Row(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            Modifier
                .padding(end = 12.dp)
                .weight(1f)
        ) {
            Text(
                text = product.name ?: "Unknown",
                style = MaterialTheme.typography.h5,
                color = Black
            )
            Text(
                text = product.unityAmount(),
                style = MaterialTheme.typography.caption,
                color = Black
            )
            Text(
                text = stringResource(R.string.preco_unitario_var, (product.unityPrice / product.amount).toMoney()),
                style = MaterialTheme.typography.caption,
                color = Black
            )
        }

        Text(
            text = product.unityPrice.toMoney(),
            style = MaterialTheme.typography.h5,
            color = Black,
            modifier = Modifier.align(Alignment.Top)
        )
    }
}