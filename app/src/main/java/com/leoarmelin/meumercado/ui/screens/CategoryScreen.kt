package com.leoarmelin.meumercado.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.leoarmelin.meumercado.ui.components.CategoryForm
import com.leoarmelin.meumercado.ui.components.DateAndBigValue
import com.leoarmelin.meumercado.ui.components.ProductForm
import com.leoarmelin.meumercado.ui.components.ProductItem
import com.leoarmelin.meumercado.ui.components.ScreenBottomSheet
import com.leoarmelin.meumercado.ui.components.ScreenColumn
import com.leoarmelin.meumercado.ui.components.ScreenHeader
import com.leoarmelin.meumercado.ui.components.SheetColumn
import com.leoarmelin.meumercado.ui.theme.Black
import com.leoarmelin.meumercado.ui.theme.CreamTwo
import com.leoarmelin.meumercado.ui.theme.GrayTwo
import com.leoarmelin.meumercado.ui.theme.Red
import com.leoarmelin.meumercado.ui.theme.Strings
import com.leoarmelin.meumercado.ui.theme.White
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

    LaunchedEffect(selectedDate) {
        categoryViewModel.setSelectedDate(selectedDate)
    }

    ScreenBottomSheet(
        content = {
            category?.let {
                Content(
                    selectedDate = selectedDate,
                    totalValue = totalValue,
                    isAddOrEditProduct = currentRoute is NavDestination.NewProduct,
                    product = (currentRoute as? NavDestination.NewProduct)?.product,
                    isEditCategory = currentRoute is NavDestination.NewCategory,
                    category = it,
                    onDateTap = { mainViewModel.toggleDatePicker(true) },
                    onSaveProduct = { id, emoji, name, unity, amount, unityPrice ->
                        mainViewModel.createOrUpdateProduct(
                            id,
                            emoji,
                            name,
                            unity,
                            amount,
                            unityPrice,
                            onSuccess = { navigationViewModel.popBack() }
                        )
                    },
                    onDeleteProduct = {
                        mainViewModel.deleteProduct(it, onSuccess = {
                            navigationViewModel.popBack()
                        })
                    },
                    onSaveCategory = { id, emoji, name ->
                        mainViewModel.createOrUpdateCategory(
                            id = id,
                            emoji = emoji,
                            name = name,
                            onSuccess = {
                                navigationViewModel.popBack()
                            }
                        )
                    },
                    onPopBack = navigationViewModel::popBack,
                    onEditTap = { navigationViewModel.setRoute(NavDestination.NewCategory(it)) },
                    onDeleteTap = {
                        mainViewModel.deleteCategory(
                            category = it,
                            onSuccess = navigationViewModel::popAllBack
                        )
                    }
                )
            }
        },
        sheetContent = {
            SheetContent(
                products = products,
                selectedProduct = (currentRoute as? NavDestination.NewProduct)?.product,
                onProductSelect = { product ->
                    if (product == null && currentRoute is NavDestination.NewProduct) {
                        navigationViewModel.popBack()
                    } else if (product != null) {
                        navigationViewModel.setRoute(NavDestination.NewProduct(product))
                    }
                }
            )
        }
    )
}

@Composable
private fun Content(
    selectedDate: LocalDateTime,
    totalValue: Double,
    isAddOrEditProduct: Boolean,
    product: Product?,
    category: Category,
    isEditCategory: Boolean,
    onDateTap: () -> Unit,
    onSaveProduct: (String?, String, String, Unity, Double, Double) -> Unit,
    onDeleteProduct: (String) -> Unit,
    onSaveCategory: (String, String, String) -> Unit,
    onPopBack: () -> Unit,
    onEditTap: () -> Unit,
    onDeleteTap: () -> Unit
) {
    var isMoreOptionsOpen by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        ScreenColumn {
            ScreenHeader(
                title = "${category.emoji} ${category.name}",
                onBack = onPopBack,
                onMoreOptions = {
                    isMoreOptionsOpen = !isMoreOptionsOpen
                }
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
                    id = product?.id,
                    emoji = emoji,
                    name = name,
                    unity = unity,
                    amount = amount,
                    unityPrice = unityPrice,
                    isButtonEnabled = name.isNotEmpty() &&
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
                    },
                    onDelete = onDeleteProduct
                )
            } else if (isEditCategory) {
                var emoji by remember(category) {
                    mutableStateOf(category.emoji)
                }
                var name by remember(category) {
                    mutableStateOf(category.name)
                }

                CategoryForm(
                    emoji = emoji,
                    name = name,
                    isButtonEnabled = emoji.isNotEmpty() && name.isNotEmpty(),
                    onEmojiChange = { emoji = it },
                    onNameChange = { name = it },
                    onSave = {
                        onSaveCategory(category.id, emoji, name)
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

        MoreOptionsMenu(
            modifier = Modifier
                .padding(top = 52.dp, end = 24.dp)
                .align(Alignment.TopEnd),
            isOpen = isMoreOptionsOpen,
            onEditTap = {
                isMoreOptionsOpen = false
                onEditTap()
            },
            onDeleteTap = {
                isMoreOptionsOpen = false
                onDeleteTap()
            }
        )
    }
}

@Composable
private fun SheetContent(
    products: List<Product>,
    onProductSelect: (Product?) -> Unit,
    selectedProduct: Product?
) {
    SheetColumn(title = Strings.Category.title) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
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
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(CreamTwo)
                    )
                }
            }
        }
    }
}

@Composable
private fun MoreOptionsMenu(
    modifier: Modifier,
    isOpen: Boolean,
    onEditTap: () -> Unit,
    onDeleteTap: () -> Unit
) {
    AnimatedVisibility(modifier = modifier, visible = isOpen) {
        Column(
            modifier = Modifier
                .shadow(5.dp)
                .background(White)
                .padding(16.dp)
        ) {
            Text(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onEditTap() },
                text = Strings.Category.editCategory,
                fontSize = 16.sp,
                color = Black
            )

            Spacer(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .width(IntrinsicSize.Max)
                    .height(1.dp)
                    .background(GrayTwo)
            )

            Text(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onDeleteTap() },
                text = Strings.Category.deleteCategory,
                fontSize = 16.sp,
                color = Red
            )
        }
    }
}