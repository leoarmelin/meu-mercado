package com.leoarmelin.meumercado.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leoarmelin.sharedmodels.api.CreateNfceRequest
import com.leoarmelin.sharedmodels.api.Result
import com.leoarmelin.sharedmodels.api.ResultState
import com.leoarmelin.meumercado.repository.ScrapperRepository
import com.leoarmelin.meumercado.repository.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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

    fun setCameraPermissionState(state: Boolean) {
        _isCameraPermissionGranted.value = state
    }

    private val _getNfceState = MutableStateFlow<ResultState?>(null)
    val getNfceState get() = _getNfceState.asStateFlow()

    fun getNfce(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _getNfceState.value = ResultState.Loading

            scrapperRepository.getNfce(CreateNfceRequest(url)).collectLatest { result ->
                when (result) {
                    is Result.Loading -> {}
                    is Result.Success -> {
                        _getNfceState.value = ResultState.Success
                    }
                    is Result.Error -> {
                        _getNfceState.value = ResultState.Error(result.exception)
                    }
                }
            }
        }
    }

    fun fetchAllCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.readAllCategories.collect {
            }
        }
    }

    fun togglePermissionDialog(state: Boolean) {
        _isCameraPermissionDialogOpen.value = state
    }

    fun clearTicketResultState() {
        _getNfceState.value = null
    }
}