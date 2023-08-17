package com.leoarmelin.meumercado.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leoarmelin.meumercado.useCases.GetDateIntervalUseCase
import com.leoarmelin.meumercado.useCases.ObserveAllCategoriesUseCase
import com.leoarmelin.sharedmodels.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val observeAllCategoriesUseCase: ObserveAllCategoriesUseCase,
    private val getDateIntervalUseCase: GetDateIntervalUseCase
): ViewModel() {
    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories get() = _categories.asStateFlow()

    private val _dateInterval = MutableStateFlow(getDateIntervalUseCase.initialInterval())
    val dateInterval get() = _dateInterval.asStateFlow()

    private val _isDatePickerOpen = MutableStateFlow(false)
    val isDatePickerOpen get() = _isDatePickerOpen.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            observeAllCategoriesUseCase.execute {
                _categories.value = it
            }
        }
    }

    fun selectDate(month: Int, year: Int) {
        _dateInterval.value = getDateIntervalUseCase.interval(month, year)
        toggleDatePicker(false)
    }

    fun toggleDatePicker(state: Boolean) {
        _isDatePickerOpen.value = state
    }
}