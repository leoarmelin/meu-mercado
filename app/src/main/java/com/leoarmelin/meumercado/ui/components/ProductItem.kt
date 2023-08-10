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
import com.leoarmelin.meumercado.ui.theme.Gray900
import com.leoarmelin.meumercado.ui.theme.MeuMercadoTheme
import com.leoarmelin.meumercado.ui.theme.Secondary800
import com.leoarmelin.sharedmodels.Unity

@Composable
fun ProductItem(product: com.leoarmelin.sharedmodels.Product) {
    Row(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = if (product.unity == Unity.UN) R.drawable.ic_egg else R.drawable.ic_balance),
            contentDescription = stringResource(
                R.string.icone_de_var,
                if (product.unity == Unity.UN) stringResource(R.string.um_ovo) else stringResource(R.string.uma_balanca)
            ),
            tint = Secondary800,
            modifier = Modifier
                .padding(end = 20.dp)
                .size(40.dp)
        )

        Column(
            Modifier
                .padding(end = 12.dp)
                .weight(1f)
        ) {
            Text(
                text = product.name ?: "Unknown",
                style = MaterialTheme.typography.h5,
                color = Secondary800
            )
            Text(
                text = product.unityAmount(),
                style = MaterialTheme.typography.caption,
                color = Gray900
            )
            Text(
                text = stringResource(R.string.preco_unitario_var, (product.unityPrice / product.amount).toMoney()),
                style = MaterialTheme.typography.caption,
                color = Gray900
            )
        }

        Text(
            text = product.unityPrice.toMoney(),
            style = MaterialTheme.typography.h5,
            color = Secondary800,
            modifier = Modifier.align(Alignment.Top)
        )
    }
}