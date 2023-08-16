package com.leoarmelin.meumercado.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.leoarmelin.meumercado.ui.components.BigTotalValue
import com.leoarmelin.meumercado.ui.components.ProductForm
import com.leoarmelin.meumercado.ui.components.ProductItem
import com.leoarmelin.meumercado.ui.components.ScreenBottomSheet
import com.leoarmelin.meumercado.ui.components.ScreenColumn
import com.leoarmelin.meumercado.ui.components.SheetColumn
import com.leoarmelin.meumercado.ui.components.StoreHeader
import com.leoarmelin.meumercado.ui.theme.CreamTwo
import com.leoarmelin.meumercado.ui.theme.Strings
import com.leoarmelin.meumercado.viewmodels.MainViewModel
import com.leoarmelin.meumercado.viewmodels.NavigationViewModel
import com.leoarmelin.meumercado.viewmodels.TicketViewModel
import com.leoarmelin.sharedmodels.Product
import com.leoarmelin.sharedmodels.Store
import com.leoarmelin.sharedmodels.Ticket
import com.leoarmelin.sharedmodels.Unity
import com.leoarmelin.sharedmodels.navigation.NavDestination

@Composable
fun TicketScreen(
    mainViewModel: MainViewModel,
    navigationViewModel: NavigationViewModel,
    ticketViewModel: TicketViewModel = hiltViewModel(),
    ticket: Ticket?
) {
    val currentRoute by navigationViewModel.currentRoute.collectAsState()
    val products by ticketViewModel.products.collectAsState()
    val store by ticketViewModel.store.collectAsState()

    val totalValue = remember(products) {
        products.sumOf { it.totalPrice }
    }
    var editingProduct by remember { mutableStateOf<Product?>(null) }

    LaunchedEffect(ticket) {
        if (ticket == null) return@LaunchedEffect
        ticketViewModel.setTicket(ticket)
    }

    ScreenBottomSheet(
        content = {
            Content(
                totalValue = totalValue,
                product = editingProduct,
                store = store,
                onSaveProduct = { id, emoji, name, unity, amount, unityPrice ->
                    mainViewModel.createOrUpdateProduct(
                        id,
                        emoji,
                        name,
                        unity,
                        amount,
                        unityPrice,
                        onSuccess = {
                            ticketViewModel.onUpdateProduct(it)
                            editingProduct = null
                        }
                    )
                },
                onDeleteProduct = {
                    mainViewModel.deleteProduct(it, onSuccess = { id ->
                        ticketViewModel.onDeleteProduct(id)
                        editingProduct = null
                    })
                },
                onPopBack = navigationViewModel::popBack,
            )
        },
        sheetContent = {
            SheetContent(
                products = products,
                selectedProduct = (currentRoute as? NavDestination.NewProduct)?.product,
                onProductSelect = { product ->
                    editingProduct = product
                }
            )
        }
    )
}

@Composable
private fun Content(
    totalValue: Double,
    product: Product?,
    store: Store?,
    onSaveProduct: (String?, String, String, Unity, Double, Double) -> Unit,
    onDeleteProduct: (String) -> Unit,
    onPopBack: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        ScreenColumn {
            if (product != null) {
                var emoji by remember {
                    mutableStateOf("")
                }
                var name by remember(product) {
                    mutableStateOf(product.name ?: "")
                }
                var unity by remember(product) {
                    mutableStateOf(product.unity)
                }
                var amount by remember(product) {
                    mutableStateOf(product.amount.toString())
                }
                var unityPrice by remember(product) {
                    mutableStateOf(product.unityPrice.toString())
                }

                ProductForm(
                    id = product.id,
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
                            product.id,
                            emoji,
                            name,
                            unity,
                            amount.toDoubleOrNull() ?: return@ProductForm,
                            unityPrice.toDoubleOrNull() ?: return@ProductForm,
                        )
                    },
                    onDelete = onDeleteProduct
                )
            } else {
                store?.let {
                    StoreHeader(
                        store = it,
                        onCloseTap = onPopBack
                    )
                }

                BigTotalValue(value = totalValue)
            }
        }
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