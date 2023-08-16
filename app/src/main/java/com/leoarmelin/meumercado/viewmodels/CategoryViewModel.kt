package com.leoarmelin.meumercado.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leoarmelin.meumercado.repository.RoomRepository
import com.leoarmelin.meumercado.ui.theme.Strings
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
    stateHandle: SavedStateHandle,
    private val roomRepository: RoomRepository
) : ViewModel() {
    private val _category = MutableStateFlow<Category?>(null)
    val category get() = _category.asStateFlow()

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products get() = _products.asStateFlow()

    init {
        stateHandle.get<String>("id")?.let { id ->
            if (id == Strings.OthersCategory.id) {
                initializeWithoutCategory()
            } else {
                initializeWithCategory(id)
            }
        }
    }

    private fun initializeWithCategory(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.getCategoryById(id).collect {
                _category.value = it
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            _category.collect {
                _products.value = roomRepository.fetchProductsFromCategory(it?.id).first()
            }
        }
    }

    private fun initializeWithoutCategory() {
        _category.value = Category(
            Strings.OthersCategory.id,
            Strings.OthersCategory.name,
            Strings.OthersCategory.emoji
        )

        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.fetchProductsWithoutCategory().collect {
                _products.value = it
            }
        }
    }
}