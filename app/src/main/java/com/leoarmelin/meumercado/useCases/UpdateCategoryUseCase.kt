package com.leoarmelin.meumercado.useCases

import com.leoarmelin.meumercado.repository.RoomRepository
import com.leoarmelin.sharedmodels.Category
import com.leoarmelin.sharedmodels.room.RoomOperation
import com.leoarmelin.sharedmodels.room.RoomResult
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateCategoryUseCase @Inject constructor(
    private val roomRepository: RoomRepository
) {
    suspend fun execute(
        category: Category
    ) = flow {
        emit(RoomResult.Loading)

        try {
            roomRepository.updateCategory(category)
            emit(RoomResult.Success(category, RoomOperation.UPDATE))
        } catch (e: Exception) {
            emit(RoomResult.Error(e.message ?: "Error during $this"))
        }
    }
}