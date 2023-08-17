package com.leoarmelin.meumercado.useCases

import com.leoarmelin.meumercado.repository.RoomRepository
import com.leoarmelin.meumercado.ui.theme.Strings
import com.leoarmelin.sharedmodels.Category
import javax.inject.Inject

class ObserveAllCategoriesUseCase @Inject constructor(
    private val roomRepository: RoomRepository
) {
    suspend fun execute(collector: (List<Category>) -> Unit) {
        roomRepository.observeAllCategories.collect { categories ->
            val newList = categories.toMutableList().also {
                it.add(
                    Category(
                        id = Strings.OthersCategory.id,
                        name = Strings.OthersCategory.name,
                        emoji = Strings.OthersCategory.emoji
                    )
                )
            }

            collector(newList)
        }
    }
}