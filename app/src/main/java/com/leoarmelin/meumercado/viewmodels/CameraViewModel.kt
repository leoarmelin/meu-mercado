package com.leoarmelin.meumercado.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CameraViewModel : ViewModel() {
    var isPermissionGranted by mutableStateOf(false)

    fun setCameraPermissionState(state: Boolean) {
        isPermissionGranted = state
    }
}