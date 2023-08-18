package com.leoarmelin.meumercado.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leoarmelin.meumercado.repository.RoomRepository
import com.leoarmelin.meumercado.ui.theme.Strings
import com.leoarmelin.sharedmodels.Category
import com.leoarmelin.sharedmodels.Product
import com.leoarmelin.sharedmodels.Unity
import com.leoarmelin.sharedmodels.api.Result
import com.leoarmelin.sharedmodels.room.RoomResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val roomRepository: RoomRepository,
    private val sharedViewModel: SharedViewModel
) : ViewModel() {

    private val _category = MutableStateFlow<Category?>(null)
    val category get() = _category.asStateFlow()

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products get() = _products.asStateFlow()

    private val _categoryResult = MutableStateFlow<Result<Any>?>(null)
    val categoryResult get() = _categoryResult.asStateFlow()

    private val _productResult = MutableStateFlow<RoomResult<Product>?>(null)
    val productResult get() = _productResult.asStateFlow()

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
            sharedViewModel.dateInterval.combine(_category) { dateInterval, category ->
                Pair(dateInterval, category)
            }.collect { (dateInterval, category) ->
                _products.value =
                    roomRepository.fetchProductsFromCategory(category?.id, dateInterval).first()
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
            sharedViewModel.dateInterval.collect { dateInterval ->
                _products.value = roomRepository.fetchProductsWithoutCategory(dateInterval).first()
            }
        }
    }

    fun updateCategory(id: String, emoji: String, name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            sharedViewModel.updateCategory(Category(id, name, emoji)).collect { result ->
                _categoryResult.value = result
            }
        }
    }

    fun deleteCategory(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            sharedViewModel.deleteCategory(id).collect { result ->
                _categoryResult.value = result
            }
        }
    }

    fun createProduct(
        emoji: String,
        name: String,
        unity: Unity,
        amount: Double,
        unityPrice: Double
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            sharedViewModel.createProduct(emoji, name, unity, amount, unityPrice).collect {
                _productResult.value = it
            }
        }
    }

    fun updateProduct(
        id: String,
        emoji: String,
        name: String,
        unity: Unity,
        amount: Double,
        unityPrice: Double
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            sharedViewModel.updateProduct(id, emoji, name, unity, amount, unityPrice).collect {
                _productResult.value = it
            }
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            sharedViewModel.deleteProduct(product).collect {
                _productResult.value = it
            }
        }
    }
}