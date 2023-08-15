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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.leoarmelin.meumercado.ui.components.CategoryForm
import com.leoarmelin.meumercado.ui.components.CategoryRow
import com.leoarmelin.meumercado.ui.components.DateAndBigValue
import com.leoarmelin.meumercado.ui.components.ScreenBottomSheet
import com.leoarmelin.meumercado.ui.components.ScreenColumn
import com.leoarmelin.meumercado.ui.components.ScreenHeader
import com.leoarmelin.meumercado.ui.components.SheetColumn
import com.leoarmelin.meumercado.ui.theme.CreamTwo
import com.leoarmelin.meumercado.ui.theme.Strings
import com.leoarmelin.meumercado.viewmodels.MainViewModel
import com.leoarmelin.meumercado.viewmodels.NavigationViewModel
import com.leoarmelin.sharedmodels.Category
import com.leoarmelin.sharedmodels.navigation.NavDestination
import java.time.LocalDateTime

@Composable
fun HomeScreen(
    mainViewModel: MainViewModel,
    navigationViewModel: NavigationViewModel,
) {
    val totalValue by mainViewModel.totalValue.collectAsState()
    val selectedDate by mainViewModel.selectedDate.collectAsState()
    val categories by mainViewModel.categories.collectAsState()
    val categoriesValue by mainViewModel.categoriesValues.collectAsState()
    val currentRoute by navigationViewModel.currentRoute.collectAsState()

    val category = remember(currentRoute) {
        (currentRoute as? NavDestination.NewCategory)?.category
    }

    ScreenBottomSheet(
        content = {
            Content(
                selectedDate = selectedDate,
                totalValue = totalValue,
                isAddOrEditCategory = currentRoute is NavDestination.NewCategory,
                category = category,
                onDateTap = { mainViewModel.toggleDatePicker(true) },
                onSaveCategory = { id, emoji, name ->
                    mainViewModel.createOrUpdateCategory(
                        id = id,
                        emoji = emoji,
                        name = name,
                        onSuccess = {
                            navigationViewModel.setRoute(NavDestination.Category(it.id))
                        }
                    )
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
    selectedDate: LocalDateTime,
    totalValue: Double,
    isAddOrEditCategory: Boolean,
    category: Category?,
    onDateTap: () -> Unit,
    onSaveCategory: (String?, String, String) -> Unit,
    onBackTap: () -> Unit
) {
    ScreenColumn {
        if (isAddOrEditCategory) {
            var emoji by remember(category) {
                mutableStateOf(category?.emoji ?: "")
            }
            var name by remember(category) {
                mutableStateOf(category?.name ?: "")
            }

            ScreenHeader(
                title = Strings.AddCategory.title,
                onBack = onBackTap
            )

            CategoryForm(
                emoji = emoji,
                name = name,
                isButtonEnabled = emoji.isNotEmpty() && name.isNotEmpty(),
                onEmojiChange = { emoji = it },
                onNameChange = { name = it },
                onSave = {
                    onSaveCategory(category?.id, emoji, name)
                }
            )
        } else {
            DateAndBigValue(
                selectedDate = selectedDate,
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
    SheetColumn(Strings.HomeSheet.title) {
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxSize(),
            contentPadding = PaddingValues(bottom = 40.dp)
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