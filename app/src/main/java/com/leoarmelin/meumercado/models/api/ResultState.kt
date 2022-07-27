package com.leoarmelin.meumercado.models.api

sealed class ResultState {
    object Loading : ResultState()
    object Success : ResultState()
    data class Error(val exception: String) : ResultState()
}
