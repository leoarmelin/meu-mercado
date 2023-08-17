package com.leoarmelin.meumercado.useCases

import com.leoarmelin.meumercado.repository.RoomRepository
import com.leoarmelin.sharedmodels.Category
import com.leoarmelin.sharedmodels.api.Result
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateCategoryUseCase @Inject constructor(
    private val roomRepository: RoomRepository
) {
    suspend fun execute(
        category: Category
    ) = flow {
        emit(Result.Loading)

        try {
            roomRepository.insertCategory(category)
            emit(Result.Success(category))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Error during $this"))
        }
    }
}