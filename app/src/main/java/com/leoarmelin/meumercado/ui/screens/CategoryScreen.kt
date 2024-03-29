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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import com.leoarmelin.sharedmodels.room.RoomOperation
import com.leoarmelin.sharedmodels.room.RoomResult
import java.time.LocalDateTime

@Composable
fun CategoryScreen(
    mainViewModel: MainViewModel,
    navigationViewModel: NavigationViewModel,
    categoryViewModel: CategoryViewModel = hiltViewModel()
) {
    val selectedDate by mainViewModel.selectedDate.collectAsStateWithLifecycle()
    val emoji by mainViewModel.emoji.collectAsStateWithLifecycle()
    val currentRoute by navigationViewModel.currentRoute.collectAsStateWithLifecycle()
    val category by categoryViewModel.category.collectAsStateWithLifecycle()
    val products by categoryViewModel.products.collectAsStateWithLifecycle()
    val categoryResult by categoryViewModel.categoryResult.collectAsStateWithLifecycle()
    val productResult by categoryViewModel.productResult.collectAsStateWithLifecycle()

    val totalValue = remember(products) {
        products.sumOf { it.totalPrice }
    }

    LaunchedEffect(categoryResult) {
        val result = categoryResult
        when {
            result is RoomResult.Success && result.operation == RoomOperation.DELETE -> navigationViewModel.popAllBack()
            result is RoomResult.Success && result.operation == RoomOperation.UPDATE -> navigationViewModel.popBack()
            else -> {}
        }
        mainViewModel.clearEmoji()
    }

    LaunchedEffect(productResult) {
        when (productResult) {
            is RoomResult.Success -> navigationViewModel.popBack()
            else -> {}
        }
        mainViewModel.clearEmoji()
    }

    ScreenBottomSheet(
        content = {
            category?.let { category ->
                Content(
                    selectedDate = selectedDate,
                    totalValue = totalValue,
                    emoji = emoji,
                    isAddOrEditProduct = currentRoute is NavDestination.NewProduct,
                    product = (currentRoute as? NavDestination.NewProduct)?.product,
                    isEditCategory = currentRoute is NavDestination.NewCategory,
                    category = category,
                    onDateTap = { mainViewModel.toggleDatePicker(true) },
                    onSaveProduct = { id, emoji, name, unity, amount, unityPrice, issueAt ->
                        if (id == null) {
                            categoryViewModel.createProduct(emoji, name, unity, amount, unityPrice)
                        } else {
                            categoryViewModel.updateProduct(
                                id,
                                emoji,
                                name,
                                unity,
                                amount,
                                unityPrice,
                                issueAt ?: return@Content
                            )
                        }
                    },
                    onDeleteProduct = { categoryViewModel.deleteProduct(it) },
                    onSaveCategory = { id, emoji, name ->
                        categoryViewModel.updateCategory(
                            id = id,
                            emoji = emoji,
                            name = name
                        )
                    },
                    onPopBack = navigationViewModel::popBack,
                    onEditTap = { navigationViewModel.setRoute(NavDestination.NewCategory(category)) },
                    onEmojiTap = { mainViewModel.toggleEmojiPicker(true) },
                    onDeleteTap = { categoryViewModel.deleteCategory(category) }
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
    selectedDate: Pair<LocalDateTime, LocalDateTime>,
    totalValue: Double,
    emoji: String,
    isAddOrEditProduct: Boolean,
    product: Product?,
    category: Category,
    isEditCategory: Boolean,
    onDateTap: () -> Unit,
    onSaveProduct: (String?, String, String, Unity, Double, Double, LocalDateTime?) -> Unit,
    onDeleteProduct: (Product) -> Unit,
    onSaveCategory: (String, String, String) -> Unit,
    onPopBack: () -> Unit,
    onEditTap: () -> Unit,
    onEmojiTap: () -> Unit,
    onDeleteTap: () -> Unit
) {
    var isMoreOptionsOpen by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        ScreenColumn {
            ScreenHeader(
                title = "${category.emoji} ${category.name}",
                onBack = onPopBack,
                onMoreOptions = if (category.id != Strings.OthersCategory.id) {
                    { isMoreOptionsOpen = !isMoreOptionsOpen }
                } else {
                    null
                }
            )

            if (isAddOrEditProduct) {
                val emojiField = remember(category, emoji) {
                    emoji.ifEmpty { category.emoji }
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
                    emoji = emojiField,
                    name = name,
                    unity = unity,
                    amount = amount,
                    unityPrice = unityPrice,
                    isButtonEnabled = name.isNotEmpty() &&
                            (amount.toDoubleOrNull() ?: 0.0) > 0 &&
                            ((unityPrice.toDoubleOrNull() ?: 0.0) > 0),
                    onEmojiTap = onEmojiTap,
                    onNameChange = { name = it },
                    onUnityChange = { unity = it },
                    onAmountChange = { amount = it },
                    onUnityPriceChange = { unityPrice = it },
                    onSave = {
                        onSaveProduct(
                            product?.id,
                            emojiField,
                            name,
                            unity,
                            amount.toDoubleOrNull() ?: return@ProductForm,
                            unityPrice.toDoubleOrNull() ?: return@ProductForm,
                            product?.issueAt
                        )
                    },
                    onDelete = {
                        if (product == null) return@ProductForm

                        onDeleteProduct(product)
                    }
                )
            } else if (isEditCategory) {
                val emojiField = remember(category, emoji) {
                    emoji.ifEmpty { category.emoji }
                }
                var name by remember(category) {
                    mutableStateOf(category.name)
                }

                CategoryForm(
                    emoji = emojiField,
                    name = name,
                    isButtonEnabled = emojiField.isNotEmpty() && name.isNotEmpty(),
                    onEmojiTap = onEmojiTap,
                    onNameChange = { name = it },
                    onSave = {
                        onSaveCategory(category.id, emojiField, name)
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