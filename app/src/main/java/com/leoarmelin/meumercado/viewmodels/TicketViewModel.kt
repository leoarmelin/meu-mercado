package com.leoarmelin.meumercado.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leoarmelin.meumercado.repository.RoomRepository
import com.leoarmelin.sharedmodels.Product
import com.leoarmelin.sharedmodels.Store
import com.leoarmelin.sharedmodels.Ticket
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(
    private val roomRepository: RoomRepository
) : ViewModel() {
    private val _ticket = MutableStateFlow<Ticket?>(null)

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products get() = _products.asStateFlow()

    private val _store = MutableStateFlow<Store?>(null)
    val store get() = _store.asStateFlow()

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
        println("Aoba - previous: ${productsMutable[index]}")
        productsMutable[index] = product
        println("Aoba - next: ${productsMutable[index]}")
        _products.value = productsMutable
    }

    private suspend fun saveAllProducts(products: List<Product>) {
        products.forEach { product ->
            roomRepository.insertProduct(product)
        }
    }
}