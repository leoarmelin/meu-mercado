package com.leoarmelin.meumercado.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.leoarmelin.meumercado.ui.theme.Secondary50
import com.leoarmelin.sharedmodels.Product

@Composable
fun ProductList(products: List<Product>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Secondary50),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(top = 38.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(products) { product ->
            ProductItem(product)
        }
    }
}