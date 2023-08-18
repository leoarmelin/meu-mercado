package com.leoarmelin.sharedmodels.room

sealed class RoomResult<out T: Any> {
    data class Success<out T: Any>(val data: T, val operation: RoomOperation) : RoomResult<T>()
    data class Error(val exception: String): RoomResult<Nothing>()
    object Loading: RoomResult<Nothing>()
}

enum class RoomOperation {
    GET,
    INSERT,
    UPDATE,
    DELETE
}
