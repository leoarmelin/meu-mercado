package com.leoarmelin.meumercado.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.leoarmelin.meumercado.ui.components.DateAndBigValue
import com.leoarmelin.meumercado.ui.components.ScreenColumn
import com.leoarmelin.meumercado.viewmodels.MainViewModel
import com.leoarmelin.meumercado.viewmodels.NavigationViewModel

@Composable
fun HomeScreen(
    mainViewModel: MainViewModel,
    navigationViewModel: NavigationViewModel,
) {
    val totalValue by mainViewModel.totalValue.collectAsState()
    val selectedDate by mainViewModel.selectedDate.collectAsState()
    ScreenColumn {
        DateAndBigValue(
            selectedDate = selectedDate,
            totalValue = totalValue,
            onDateTap = { mainViewModel.toggleDatePicker(true) }
        )
    }
}