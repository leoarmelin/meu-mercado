package com.leoarmelin.meumercado.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leoarmelin.meumercado.useCases.FetchCategoriesValuesUseCase
import com.leoarmelin.sharedmodels.Category
import com.leoarmelin.sharedmodels.room.RoomResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val sharedViewModel: SharedViewModel,
    private val fetchCategoriesValuesUseCase: FetchCategoriesValuesUseCase
) : ViewModel() {

    val categories = sharedViewModel.categories

    private val _categoryResult = MutableStateFlow<RoomResult<Category>?>(null)
    val categoryResult get() = _categoryResult.asStateFlow()

    private val _categoriesValues = MutableStateFlow<Map<String, Double>>(HashMap())
    val categoriesValues get() = _categoriesValues.asStateFlow()

    private val _totalValue = MutableStateFlow(0.0)
    val totalValue get() = _totalValue.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            fetchCategoriesValuesUseCase.execute().collect { list ->
                val map = _categoriesValues.value.toMutableMap()
                list.forEach {
                    map[it.first] = it.second
                }
                _categoriesValues.value = map
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            _categoriesValues.collect {
                _totalValue.value = it.values.sum()
            }
        }
    }

    fun createCategory(
        emoji: String,
        name: String,
    ) {
        val newCategory = Category(UUID.randomUUID().toString(), name, emoji)

        viewModelScope.launch(Dispatchers.IO) {
            sharedViewModel.createCategory(newCategory).collect {
                _categoryResult.value = it
            }
        }
    }
}