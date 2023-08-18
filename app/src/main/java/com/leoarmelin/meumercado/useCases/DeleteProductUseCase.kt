package com.leoarmelin.meumercado.useCases

import com.leoarmelin.meumercado.repository.RoomRepository
import com.leoarmelin.sharedmodels.Product
import com.leoarmelin.sharedmodels.room.RoomOperation
import com.leoarmelin.sharedmodels.room.RoomResult
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteProductUseCase @Inject constructor(
    private val roomRepository: RoomRepository,
) {
    suspend fun execute(product: Product) = flow {
        emit(RoomResult.Loading)

        try {
            roomRepository.deleteProductById(product.id)

            emit(RoomResult.Success(product, RoomOperation.DELETE))
        } catch (e: Exception) {
            emit(RoomResult.Error(e.message ?: "Error during $this"))
        }
    }
}