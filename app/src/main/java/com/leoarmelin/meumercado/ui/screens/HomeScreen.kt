package com.leoarmelin.meumercado.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.leoarmelin.meumercado.viewmodels.MainViewModel
import com.leoarmelin.meumercado.viewmodels.NavigationViewModel

@Composable
fun HomeScreen(
    mainViewModel: MainViewModel,
    navigationViewModel: NavigationViewModel,
    onClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "One", modifier = Modifier.clickable { onClick() })
    }
}