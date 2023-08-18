package com.leoarmelin.meumercado.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leoarmelin.meumercado.repository.RoomRepository
import com.leoarmelin.sharedmodels.Product
import com.leoarmelin.sharedmodels.Store
import com.leoarmelin.sharedmodels.Ticket
import com.leoarmelin.sharedmodels.Unity
import com.leoarmelin.sharedmodels.room.RoomResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    private val sharedViewModel: SharedViewModel
) : ViewModel() {
    private val _ticket = MutableStateFlow<Ticket?>(null)

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products get() = _products.asStateFlow()

    private val _store = MutableStateFlow<Store?>(null)
    val store get() = _store.asStateFlow()

    private val _productResult = MutableStateFlow<RoomResult<Product>?>(null)
    val productResult get() = _productResult.asStateFlow()

    private val _editingProduct = MutableStateFlow<Product?>(null)
    val editingProduct get() = _editingProduct.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _ticket.collect {
                if (it == null) return@collect

                saveAllProducts(it.products)

                _products.value = it.products
                _store.value = it.store
            }
        }
    }

    fun setTicket(ticket: Ticket) {
        _ticket.value = ticket
    }

    fun onUpdateProduct(product: Product) {
        val productsMutable = _products.value.toMutableList()
        val index = productsMutable.indexOfFirst { it.id == product.id }
        if (index == -1) return
        productsMutable[index] = product
        _products.value = productsMutable
    }

    fun onDeleteProduct(id: String) {
        val productsMutable = _products.value.toMutableList()
        productsMutable.removeIf { it.id == id }
        _products.value = productsMutable
    }

    private suspend fun saveAllProducts(products: List<Product>) {
        products.forEach { product ->
            roomRepository.insertProduct(product)
        }
    }

    fun updateProduct(
        id: String,
        emoji: String,
        name: String,
        unity: Unity,
        amount: Double,
        unityPrice: Double,
        issueAt: LocalDateTime
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            sharedViewModel.updateProduct(id, emoji, name, unity, amount, unityPrice, issueAt).collect {
                _productResult.value = it
            }
        }
    }

    fun setEditingProduct(value: Product) {
        _editingProduct.value = value
    }

    fun clearEditingProduct() {
        _editingProduct.value = null
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            sharedViewModel.deleteProduct(product).collect {
                _productResult.value = it
            }
        }
    }
}