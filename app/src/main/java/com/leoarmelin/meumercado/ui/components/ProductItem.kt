package com.leoarmelin.meumercado.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.leoarmelin.meumercado.R
import com.leoarmelin.meumercado.extensions.toMoney
import com.leoarmelin.meumercado.extensions.unityAmount
import com.leoarmelin.meumercado.ui.theme.Black
import com.leoarmelin.meumercado.ui.theme.CreamOne
import com.leoarmelin.meumercado.ui.theme.CreamTwo
import com.leoarmelin.meumercado.ui.theme.MeuMercadoTheme
import com.leoarmelin.sharedmodels.Product
import com.leoarmelin.sharedmodels.mock

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductItem(
    product: Product,
    isSelected: Boolean,
    onLongPress: (Product) -> Unit,
    onTap: () -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) CreamTwo else CreamOne,
        label = "backgroundColor"
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .combinedClickable(
                onLongClick = {
                    onLongPress(product)
                },
                onClick = onTap
            )
            .padding(horizontal = 24.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            Modifier
                .padding(end = 12.dp)
                .weight(1f)
        ) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.h5,
                color = Black
            )
            Text(
                text = product.unityAmount(),
                style = MaterialTheme.typography.caption,
                color = Black
            )
            Text(
                text = stringResource(R.string.preco_unitario_var, product.unityPrice.toMoney()),
                style = MaterialTheme.typography.caption,
                color = Black
            )
        }

        Text(
            text = product.totalPrice.toMoney(),
            style = MaterialTheme.typography.h5,
            color = Black
        )
    }
}

@Preview
@Composable
private fun PreviewOne() {
    var isSelected by remember { mutableStateOf(false) }
    MeuMercadoTheme {
        ProductItem(
            product = Product.mock,
            isSelected = isSelected,
            onLongPress = { isSelected = !isSelected },
            onTap = {}
        )
    }
}