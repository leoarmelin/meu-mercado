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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val roomRepository: RoomRepository
) : ViewModel() {

    private val _selectedDate = MutableStateFlow<LocalDateTime?>(null)

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
            _selectedDate.combine(_category) { startDate, category ->
                Pair(startDate, category)
            }.collect { (startDate, category) ->
                if (startDate == null) return@collect
                val endDate = startDate.plusMonths(1).minusSeconds(2)

                _products.value = roomRepository.fetchProductsFromCategory(category?.id, startDate, endDate).first()
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
            _selectedDate.collect { startDate ->
                if (startDate == null) return@collect
                val endDate = startDate.plusMonths(1).minusSeconds(2)

                _products.value = roomRepository.fetchProductsWithoutCategory(startDate, endDate).first()
            }
        }


    }

    fun setSelectedDate(value: LocalDateTime) {
        _selectedDate.value = value
    }
}