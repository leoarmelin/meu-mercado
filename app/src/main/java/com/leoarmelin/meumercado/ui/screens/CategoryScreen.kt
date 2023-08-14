package com.leoarmelin.meumercado.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.leoarmelin.meumercado.ui.components.DateAndBigValue
import com.leoarmelin.meumercado.ui.components.ScreenColumn
import com.leoarmelin.meumercado.ui.components.ScreenHeader
import com.leoarmelin.meumercado.viewmodels.CategoryViewModel
import com.leoarmelin.meumercado.viewmodels.MainViewModel
import com.leoarmelin.meumercado.viewmodels.NavigationViewModel

@Composable
fun CategoryScreen(
    categoryViewModel: CategoryViewModel,
    navigationViewModel: NavigationViewModel,
    mainViewModel: MainViewModel
) {
    val category by categoryViewModel.category.collectAsState()
    val categoriesValues by mainViewModel.categoriesValues.collectAsState()
    val selectedDate by mainViewModel.selectedDate.collectAsState()

    val totalValue = remember(category, categoriesValues) {
        categoriesValues[category?.id] ?: 0.0
    }

    ScreenColumn {
        ScreenHeader(title = "${category?.emoji} ${category?.name}", onBack = navigationViewModel::popBack)

        DateAndBigValue(
            selectedDate = selectedDate,
            totalValue = totalValue,
            onDateTap = { mainViewModel.toggleDatePicker(true) }
        )
    }
}