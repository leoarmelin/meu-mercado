package com.leoarmelin.meumercado.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.leoarmelin.meumercado.ui.components.CategoryForm
import com.leoarmelin.meumercado.ui.components.CategoryRow
import com.leoarmelin.meumercado.ui.components.DateAndBigValue
import com.leoarmelin.meumercado.ui.components.ScreenBottomSheet
import com.leoarmelin.meumercado.ui.components.ScreenColumn
import com.leoarmelin.meumercado.ui.components.ScreenHeader
import com.leoarmelin.meumercado.ui.components.SheetColumn
import com.leoarmelin.meumercado.ui.theme.CreamTwo
import com.leoarmelin.meumercado.ui.theme.Strings
import com.leoarmelin.meumercado.viewmodels.HomeViewModel
import com.leoarmelin.meumercado.viewmodels.MainViewModel
import com.leoarmelin.meumercado.viewmodels.NavigationViewModel
import com.leoarmelin.sharedmodels.Category
import com.leoarmelin.sharedmodels.navigation.NavDestination
import com.leoarmelin.sharedmodels.room.RoomResult
import java.time.LocalDateTime

@Composable
fun HomeScreen(
    mainViewModel: MainViewModel,
    navigationViewModel: NavigationViewModel,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val selectedDate by mainViewModel.selectedDate.collectAsStateWithLifecycle()
    val emoji by mainViewModel.emoji.collectAsStateWithLifecycle()
    val currentRoute by navigationViewModel.currentRoute.collectAsStateWithLifecycle()
    val totalValue by homeViewModel.totalValue.collectAsStateWithLifecycle()
    val categories by homeViewModel.categories.collectAsStateWithLifecycle()
    val categoriesValue by homeViewModel.categoriesValues.collectAsStateWithLifecycle()
    val categoryResult by homeViewModel.categoryResult.collectAsStateWithLifecycle()

    LaunchedEffect(categoryResult) {
        when (val result = categoryResult) {
            is RoomResult.Success -> {
                navigationViewModel.setRoute(NavDestination.Category(result.data.id))
            }
            else -> {}
        }
    }

    ScreenBottomSheet(
        content = {
            Content(
                selectedDate = selectedDate,
                totalValue = totalValue,
                emoji = emoji,
                isAddOrEditCategory = currentRoute is NavDestination.NewCategory,
                onDateTap = { mainViewModel.toggleDatePicker(true) },
                onEmojiTap = { mainViewModel.toggleEmojiPicker(true) },
                onSaveCategory = { emoji, name ->
                    homeViewModel.createCategory(emoji, name)
                },
                onBackTap = navigationViewModel::popBack
            )
        },
        sheetContent = {
            SheetContent(
                categories = categories,
                categoriesValue = categoriesValue,
                onCategoryTap = { navigationViewModel.setRoute(NavDestination.Category(it.id)) })
        }
    )
}

@Composable
private fun Content(
    selectedDate: Pair<LocalDateTime, LocalDateTime>,
    totalValue: Double,
    emoji: String,
    isAddOrEditCategory: Boolean,
    onDateTap: () -> Unit,
    onEmojiTap: () -> Unit,
    onSaveCategory: (String, String) -> Unit,
    onBackTap: () -> Unit
) {
    ScreenColumn {
        if (isAddOrEditCategory) {
            var name by remember {
                mutableStateOf("")
            }

            ScreenHeader(
                title = Strings.AddCategory.title,
                onBack = onBackTap
            )

            CategoryForm(
                emoji = emoji,
                name = name,
                isButtonEnabled = emoji.isNotEmpty() && name.isNotEmpty(),
                onEmojiTap = onEmojiTap,
                onNameChange = { name = it },
                onSave = {
                    onSaveCategory(emoji, name)
                }
            )
        } else {
            DateAndBigValue(
                date = selectedDate.first,
                totalValue = totalValue,
                onDateTap = onDateTap
            )
        }
    }
}

@Composable
private fun SheetContent(
    categories: List<Category>,
    categoriesValue: Map<String, Double>,
    onCategoryTap: (Category) -> Unit
) {
    SheetColumn(Strings.Home.title) {
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxSize(),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            itemsIndexed(
                categories,
                key = { _, category -> category.id }
            ) { index, category ->
                CategoryRow(
                    category = category,
                    amount = categoriesValue[category.id] ?: 0.0,
                    onTap = {
                        onCategoryTap(it)
                    }
                )

                if (index < categories.size - 1) {
                    Spacer(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(CreamTwo)
                    )
                }
            }
        }
    }
}