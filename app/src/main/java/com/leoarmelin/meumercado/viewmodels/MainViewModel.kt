package com.leoarmelin.meumercado.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leoarmelin.sharedmodels.api.CreateNfceRequest
import com.leoarmelin.sharedmodels.api.Result
import com.leoarmelin.meumercado.repository.ScrapperRepository
import com.leoarmelin.meumercado.repository.RoomRepository
import com.leoarmelin.sharedmodels.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
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

    private val _nfceState = MutableStateFlow<Result<String>?>(null)
    val nfceState get() = _nfceState.asStateFlow()

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories get() = _categories.asStateFlow()

    private val _categoriesValues = MutableStateFlow<Map<String, Double>>(HashMap())
    val categoriesValues get() = _categoriesValues.asStateFlow()

    private val _totalValue = MutableStateFlow(0.0)
    val totalValue get() = _totalValue.asStateFlow()

    private val _isDatePickerOpen = MutableStateFlow(false)
    val isDatePickerOpen get() = _isDatePickerOpen.asStateFlow()

    private val _selectedDate = MutableStateFlow(LocalDateTime.now())
    val selectedDate get() = _selectedDate.asStateFlow()

    init {
        fetchAllCategories()

        viewModelScope.launch {
            _categories.collect {
                fetchAllCategoriesValues(it)
            }
        }

        viewModelScope.launch {
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
            _nfceState.value = Result.Loading

            scrapperRepository.getNfce(CreateNfceRequest(url)).collectLatest { result ->
                when (result) {
                    is Result.Loading -> {}
                    is Result.Success -> {
                        _nfceState.value = Result.Success("success")
                    }

                    is Result.Error -> {
                        _nfceState.value = Result.Error(result.exception)
                    }
                }
            }
        }
    }

    private fun fetchAllCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.readAllCategories.collect {
                _categories.value = it
            }
        }
    }

    private suspend fun fetchAllCategoriesValues(categories: List<Category>) {
        val values = _categoriesValues.value.toMutableMap()
        categories.forEach { category ->
            val amount = roomRepository.getTotalAmountFromCategoryId(category.id).first() ?: 0.0

            values[category.id] = amount
        }
        _categoriesValues.value = values
    }

    fun togglePermissionDialog(state: Boolean) {
        _isCameraPermissionDialogOpen.value = state
    }

    fun toggleDatePicker(state: Boolean) {
        _isDatePickerOpen.value = state
    }

    fun clearTicketResult() {
        _nfceState.value = null
    }

    fun selectDate(month: Int, year: Int) {
        _selectedDate.value = LocalDateTime.of(year, month, 1, 12, 0, 0)
        toggleDatePicker(false)
    }

    fun createCategory(
        emoji: String,
        name: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.insertCategory(
                Category(
                    id = UUID.randomUUID().toString(),
                    name = name,
                    emoji = emoji
                )
            )
        }
    }

    private fun createProduct() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(5000)
//            roomRepository.insertProduct(Product(
//                id = UUID.randomUUID().toString(),
//                name = "Product One",
//                unity = Unity.UN,
//                amount = 2.0,
//                unityPrice = 5.0,
//                totalPrice = 10.0,
//                issueAt = "2023-08-10T13:16:30.0000Z",
//                categoryId = _categories.value.first().id
//            ))
        }
    }
}