package com.leoarmelin.meumercado.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leoarmelin.sharedmodels.api.CreateNfceRequest
import com.leoarmelin.sharedmodels.api.Result
import com.leoarmelin.meumercado.repository.ScrapperRepository
import com.leoarmelin.meumercado.repository.RoomRepository
import com.leoarmelin.meumercado.ui.theme.Strings
import com.leoarmelin.sharedmodels.Category
import com.leoarmelin.sharedmodels.Product
import com.leoarmelin.sharedmodels.Ticket
import com.leoarmelin.sharedmodels.Unity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val scrapperRepository: ScrapperRepository,
    private val roomRepository: RoomRepository
) : ViewModel() {

    private val _isCameraPermissionDialogOpen = MutableStateFlow(false)
    val isCameraPermissionDialogOpen get() = _isCameraPermissionDialogOpen.asStateFlow()

    private val _isCameraPermissionGranted = MutableStateFlow(false)
    val isCameraPermissionGranted get() = _isCameraPermissionGranted.asStateFlow()

    private val _ticketResult = MutableStateFlow<Result<Ticket>?>(null)
    val ticketResult get() = _ticketResult.asStateFlow()

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories get() = _categories.asStateFlow()

    private val _categoriesValues = MutableStateFlow<Map<String, Double>>(HashMap())
    val categoriesValues get() = _categoriesValues.asStateFlow()

    private val _totalValue = MutableStateFlow(0.0)
    val totalValue get() = _totalValue.asStateFlow()

    private val _isDatePickerOpen = MutableStateFlow(false)
    val isDatePickerOpen get() = _isDatePickerOpen.asStateFlow()

    private val _selectedDate = MutableStateFlow(getInitialDate())
    val selectedDate get() = _selectedDate.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.observeAllCategories.collect {
                val othersCategory = Category(
                    id = Strings.OthersCategory.id,
                    name = Strings.OthersCategory.name,
                    emoji = Strings.OthersCategory.emoji
                )
                _categories.value = it + othersCategory
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            combine(roomRepository.observeAllProducts, _categories, _selectedDate) { _, categories, startDate ->
                Pair(startDate, categories)
            }.collect { (startDate, categories) ->
                fetchAllCategoriesValues(startDate, categories)
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            _categoriesValues.collect {
                _totalValue.value = it.values.sum()
            }
        }
    }

    fun setCameraPermissionState(state: Boolean) {
        _isCameraPermissionGranted.value = state
    }

    fun getNfce(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _ticketResult.value = Result.Loading

            scrapperRepository.getNfce(CreateNfceRequest(url)).collectLatest { result ->
                when (result) {
                    is Result.Loading -> {}
                    is Result.Success -> {
                        _ticketResult.value = result
                    }

                    is Result.Error -> {
                        _ticketResult.value = Result.Error(result.exception)
                    }
                }
            }
        }
    }

    private suspend fun fetchAllCategoriesValues(startDate: LocalDateTime, categories: List<Category>) {
        val endDate = startDate.plusMonths(1).minusSeconds(2)
        val values = _categoriesValues.value.toMutableMap()
        categories.forEach { category ->
            val amount = roomRepository.getTotalAmountFromCategoryId(category.id, startDate, endDate).first() ?: 0.0

            values[category.id] = amount
        }

        val othersAmount = roomRepository.getTotalAmountWithoutCategory(startDate, endDate).first() ?: 0.0
        values[Strings.OthersCategory.id] = othersAmount

        _categoriesValues.value = values
    }

    fun togglePermissionDialog(state: Boolean) {
        _isCameraPermissionDialogOpen.value = state
    }

    fun toggleDatePicker(state: Boolean) {
        _isDatePickerOpen.value = state
    }

    fun clearTicketResult() {
        _ticketResult.value = null
    }

    private fun getInitialDate(): LocalDateTime {
        val now = LocalDateTime.now()
        return LocalDateTime.of(now.year, now.monthValue, 1, 0, 0, 1)
    }

    fun selectDate(month: Int, year: Int) {
        _selectedDate.value = LocalDateTime.of(year, month, 1, 0, 0, 1)
        toggleDatePicker(false)
    }

    fun createOrUpdateCategory(
        id: String?,
        emoji: String,
        name: String,
        onSuccess: (Category) -> Unit
    ) {
        val category = Category(
            id = id ?: UUID.randomUUID().toString(),
            name = name,
            emoji = emoji
        )

        viewModelScope.launch(Dispatchers.IO) {
            if (id == null) {
                roomRepository.insertCategory(category)
            } else {
                roomRepository.updateCategory(category)
            }

            onSuccess(category)
        }
    }

    fun createOrUpdateProduct(
        id: String?,
        emoji: String,
        name: String,
        unity: Unity,
        amount: Double,
        unityPrice: Double,
        onSuccess: (Product) -> Unit
    ) {
        val category = _categories.value.find { it.emoji == emoji }
        val product = Product(
            id = id ?: UUID.randomUUID().toString(),
            name = name,
            unity = unity,
            amount = amount,
            unityPrice = unityPrice,
            totalPrice = amount * unityPrice,
            issueAt = LocalDateTime.now(),
            categoryId = if (emoji == Strings.OthersCategory.emoji) null else category?.id
        )

        viewModelScope.launch(Dispatchers.IO) {
            if (id == null) {
                roomRepository.insertProduct(product)
            } else {
                roomRepository.updateProduct(product)
            }

            onSuccess(product)
        }
    }

    fun deleteCategory(category: Category, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.deleteCategoryById(category.id)
            onSuccess()
        }
    }

    fun deleteProduct(id: String, onSuccess: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.deleteProductById(id)
            onSuccess(id)
        }
    }
}