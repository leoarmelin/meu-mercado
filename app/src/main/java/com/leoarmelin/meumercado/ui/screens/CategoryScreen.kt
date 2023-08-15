package com.leoarmelin.meumercado.ui.screens

import androidx.compose.foundation.background
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.leoarmelin.meumercado.ui.components.DateAndBigValue
import com.leoarmelin.meumercado.ui.components.ProductForm
import com.leoarmelin.meumercado.ui.components.ProductItem
import com.leoarmelin.meumercado.ui.components.ScreenBottomSheet
import com.leoarmelin.meumercado.ui.components.ScreenColumn
import com.leoarmelin.meumercado.ui.components.ScreenHeader
import com.leoarmelin.meumercado.ui.components.SheetColumn
import com.leoarmelin.meumercado.ui.theme.CreamTwo
import com.leoarmelin.meumercado.ui.theme.Strings
import com.leoarmelin.meumercado.viewmodels.CategoryViewModel
import com.leoarmelin.meumercado.viewmodels.MainViewModel
import com.leoarmelin.meumercado.viewmodels.NavigationViewModel
import com.leoarmelin.sharedmodels.Category
import com.leoarmelin.sharedmodels.Product
import com.leoarmelin.sharedmodels.Unity
import com.leoarmelin.sharedmodels.navigation.NavDestination
import java.time.LocalDateTime

@Composable
fun CategoryScreen(
    mainViewModel: MainViewModel,
    navigationViewModel: NavigationViewModel,
    categoryViewModel: CategoryViewModel = hiltViewModel()
) {
    val category by categoryViewModel.category.collectAsState()
    val products by categoryViewModel.products.collectAsState()
    val selectedDate by mainViewModel.selectedDate.collectAsState()
    val currentRoute by navigationViewModel.currentRoute.collectAsState()

    val totalValue = remember(products) {
        products.sumOf { it.totalPrice }
    }

    ScreenBottomSheet(
        content = {
            category?.let {
                Content(
                    category = it,
                    selectedDate = selectedDate,
                    totalValue = totalValue,
                    isAddOrEditProduct = currentRoute is NavDestination.NewProduct,
                    product = (currentRoute as? NavDestination.NewProduct)?.product,
                    onDateTap = { mainViewModel.toggleDatePicker(true) },
                    onSaveProduct = { id, emoji, name, unity, amount, unityPrice ->
                        mainViewModel.createOrUpdateProduct(
                            id,
                            emoji,
                            name,
                            unity,
                            amount,
                            unityPrice,
                            onSuccess = navigationViewModel::popBack
                        )
                    },
                    onPopBack = navigationViewModel::popBack
                )
            }
        },
        sheetContent = {
            SheetContent(
                products = products,
                selectedProduct = (currentRoute as? NavDestination.NewProduct)?.product,
                onProductSelect = { product ->
                    if (product == null) {
                        navigationViewModel.popBack()
                    } else {
                        navigationViewModel.setRoute(NavDestination.NewProduct(product))
                    }

                }
            )
        }
    )
}

@Composable
private fun Content(
    category: Category,
    selectedDate: LocalDateTime,
    totalValue: Double,
    isAddOrEditProduct: Boolean,
    product: Product?,
    onDateTap: () -> Unit,
    onSaveProduct: (String?, String, String, Unity, Double, Double) -> Unit,
    onPopBack: () -> Unit
) {
    ScreenColumn {
        ScreenHeader(
            title = "${category.emoji} ${category.name}",
            onBack = onPopBack
        )

        if (isAddOrEditProduct) {
            var emoji by remember(category) {
                mutableStateOf(category.emoji)
            }
            var name by remember(product) {
                mutableStateOf(product?.name ?: "")
            }
            var unity by remember(product) {
                mutableStateOf(product?.unity ?: Unity.UN)
            }
            var amount by remember(product) {
                mutableStateOf(product?.amount?.toString() ?: "")
            }
            var unityPrice by remember(product) {
                mutableStateOf(product?.unityPrice?.toString() ?: "")
            }

            ProductForm(
                emoji = emoji,
                name = name,
                unity = unity,
                amount = amount,
                unityPrice = unityPrice,
                isButtonEnabled = emoji.isNotEmpty() &&
                        name.isNotEmpty() &&
                        (amount.toDoubleOrNull() ?: 0.0) > 0 &&
                        ((unityPrice.toDoubleOrNull() ?: 0.0) > 0),
                onEmojiChange = { emoji = it },
                onNameChange = { name = it },
                onUnityChange = { unity = it },
                onAmountChange = { amount = it },
                onUnityPriceChange = { unityPrice = it },
                onSave = {
                    onSaveProduct(
                        product?.id,
                        emoji,
                        name,
                        unity,
                        amount.toDoubleOrNull() ?: return@ProductForm,
                        unityPrice.toDoubleOrNull() ?: return@ProductForm,
                    )
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
    products: List<Product>,
    onProductSelect: (Product?) -> Unit,
    selectedProduct: Product?
) {
    SheetColumn(title = Strings.CategorySheet.title) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(
                products,
                key = { _, product -> product.id }
            ) { index, product ->
                ProductItem(
                    product = product,
                    isSelected = product.id == selectedProduct?.id,
                    onTap = { onProductSelect(null) },
                    onLongPress = onProductSelect
                )

                if (index < products.size - 1) {
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