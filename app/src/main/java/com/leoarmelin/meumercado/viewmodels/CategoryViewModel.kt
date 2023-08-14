package com.leoarmelin.meumercado.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leoarmelin.meumercado.repository.RoomRepository
import com.leoarmelin.sharedmodels.Category
import com.leoarmelin.sharedmodels.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val roomRepository: RoomRepository
) : ViewModel() {
    private val _category = MutableStateFlow<Category?>(null)
    val category get() = _category.asStateFlow()

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products get() = _products.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _category.collect {
                if (it == null) return@collect
                _products.value = roomRepository.fetchProductsFromCategory(it.id).first()
            }
        }
    }

    fun setCategoryId(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _category.value = roomRepository.getCategoryById(id).first()
        }
    }
}