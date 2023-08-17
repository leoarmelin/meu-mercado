package com.leoarmelin.meumercado.useCases

import com.leoarmelin.meumercado.repository.RoomRepository
import com.leoarmelin.sharedmodels.api.Result
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteCategoryUseCase @Inject constructor(
    private val roomRepository: RoomRepository
) {
    suspend fun execute(id: String) = flow {
        emit(Result.Loading)

        try {
            roomRepository.deleteCategoryById(id)
            emit(Result.Success("Success"))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Error during $this"))
        }
    }
}