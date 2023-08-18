package com.leoarmelin.meumercado.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val sharedViewModel: SharedViewModel
) : ViewModel() {

    private val _categoryResult = MutableStateFlow<RoomResult<Category>?>(null)
    val categoryResult get() = _categoryResult.asStateFlow()

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