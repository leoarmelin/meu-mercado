package com.leoarmelin.meumercado.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leoarmelin.meumercado.extensions.toMoney
import com.leoarmelin.meumercado.ui.theme.Black
import com.leoarmelin.meumercado.ui.theme.MeuMercadoTheme
import com.leoarmelin.sharedmodels.Category
import com.leoarmelin.sharedmodels.mock

@Composable
fun CategoryRow(category: Category, amount: Double, onTap: (Category) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .clickable { onTap(category) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = category.emoji, fontSize = 16.sp)

            Text(
                text = category.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Black
            )
        }

        Text(
            text = amount.toMoney(),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Black
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun PreviewOne() {
    MeuMercadoTheme {
        CategoryRow(
            category = Category.mock,
            amount = 20.0,
            onTap = {}
        )
    }
}