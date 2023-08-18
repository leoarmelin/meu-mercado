package com.leoarmelin.meumercado.useCases

import com.leoarmelin.meumercado.repository.RoomRepository
import com.leoarmelin.meumercado.ui.theme.Strings
import com.leoarmelin.sharedmodels.Category
import com.leoarmelin.sharedmodels.Product
import com.leoarmelin.sharedmodels.Unity
import com.leoarmelin.sharedmodels.room.RoomOperation
import com.leoarmelin.sharedmodels.room.RoomResult
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class CreateProductUseCase @Inject constructor(
    private val roomRepository: RoomRepository,
) {
    suspend fun execute(
        emoji: String,
        name: String,
        unity: Unity,
        amount: Double,
        unityPrice: Double,
        categories: List<Category>
    ) = flow {
        emit(RoomResult.Loading)

        try {
            val category = categories.find { it.emoji == emoji }
            val product = Product(
                id = UUID.randomUUID().toString(),
                name = name,
                unity = unity,
                amount = amount,
                unityPrice = unityPrice,
                totalPrice = amount * unityPrice,
                issueAt = LocalDateTime.now(),
                categoryId = if (emoji == Strings.OthersCategory.emoji) null else category?.id
            )

            roomRepository.insertProduct(product)

            emit(RoomResult.Success(product, RoomOperation.INSERT))
        } catch (e: Exception) {
            emit(RoomResult.Error(e.message ?: "Error during $this"))
        }
    }
}