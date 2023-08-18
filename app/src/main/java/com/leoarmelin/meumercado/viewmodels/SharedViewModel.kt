package com.leoarmelin.meumercado.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leoarmelin.meumercado.useCases.CreateCategoryUseCase
import com.leoarmelin.meumercado.useCases.CreateProductUseCase
import com.leoarmelin.meumercado.useCases.DeleteCategoryUseCase
import com.leoarmelin.meumercado.useCases.DeleteProductUseCase
import com.leoarmelin.meumercado.useCases.GetDateIntervalUseCase
import com.leoarmelin.meumercado.useCases.ObserveAllCategoriesUseCase
import com.leoarmelin.meumercado.useCases.UpdateCategoryUseCase
import com.leoarmelin.meumercado.useCases.UpdateProductUseCase
import com.leoarmelin.sharedmodels.Category
import com.leoarmelin.sharedmodels.Product
import com.leoarmelin.sharedmodels.Unity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val observeAllCategoriesUseCase: ObserveAllCategoriesUseCase,
    private val getDateIntervalUseCase: GetDateIntervalUseCase,
    private val createCategoryUseCase: CreateCategoryUseCase,
    private val updateCategoryUseCase: UpdateCategoryUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase,
    private val createProductUseCase: CreateProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase
) : ViewModel() {
    val categories = MutableStateFlow<List<Category>>(emptyList())

    val dateInterval = MutableStateFlow(getDateIntervalUseCase.initialInterval())

    private val _isDatePickerOpen = MutableStateFlow(false)
    val isDatePickerOpen get() = _isDatePickerOpen.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            observeAllCategoriesUseCase.execute {
                categories.value = it
            }
        }
    }

    fun selectDate(month: Int, year: Int) {
        dateInterval.value = getDateIntervalUseCase.interval(month, year)
        toggleDatePicker(false)
    }

    fun toggleDatePicker(state: Boolean) {
        _isDatePickerOpen.value = state
    }

    suspend fun createCategory(category: Category) = createCategoryUseCase.execute(category)

    suspend fun updateCategory(category: Category) = updateCategoryUseCase.execute(category)

    suspend fun deleteCategory(category: Category) = deleteCategoryUseCase.execute(category)

    suspend fun createProduct(
        emoji: String,
        name: String,
        unity: Unity,
        amount: Double,
        unityPrice: Double
    ) = createProductUseCase.execute(
        emoji,
        name,
        unity,
        amount,
        unityPrice,
        categories.value,
        dateInterval.value.first
    )

    suspend fun updateProduct(
        id: String,
        emoji: String,
        name: String,
        unity: Unity,
        amount: Double,
        unityPrice: Double,
        issueAt: LocalDateTime,
    ) = updateProductUseCase.execute(
        id,
        emoji,
        name,
        unity,
        amount,
        unityPrice,
        issueAt,
        categories.value
    )

    suspend fun deleteProduct(product: Product) = deleteProductUseCase.execute(product)
}