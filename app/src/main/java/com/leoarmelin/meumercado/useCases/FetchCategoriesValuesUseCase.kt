package com.leoarmelin.meumercado.useCases

import com.leoarmelin.meumercado.repository.RoomRepository
import com.leoarmelin.meumercado.ui.theme.Strings
import com.leoarmelin.meumercado.viewmodels.SharedViewModel
import com.leoarmelin.sharedmodels.Category
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDateTime
import javax.inject.Inject

class FetchCategoriesValuesUseCase @Inject constructor(
    private val roomRepository: RoomRepository,
    private val sharedViewModel: SharedViewModel
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun execute() = flow {
        combine(
            roomRepository.observeAllProducts,
            sharedViewModel.categories,
            sharedViewModel.dateInterval
        ) { products, categories, dateInterval ->
            flowOf(Pair(products, Pair(categories, dateInterval)))
        }.flatMapConcat { it }.filter { it.first.isNotEmpty() }.collect { (_, pair) ->
            val one = getTotalAmounts(pair.first, pair.second)
            val two = getNoCategoryTotalAmount(pair.second)

            emit(one + two)
        }
    }

    suspend fun getTotalAmounts(
        categories: List<Category>,
        dateInterval: Pair<LocalDateTime, LocalDateTime>
    ): List<Pair<String, Double>> {
        val list = mutableListOf<Pair<String, Double>>()
        categories.filter { it.id != Strings.OthersCategory.id }.forEach { category ->
            list.add(
                Pair(
                    category.id,
                    roomRepository.getTotalAmountFromCategoryId(category.id, dateInterval).first()
                        ?: 0.0
                )
            )
        }
        return list.toList()
    }

    private suspend fun getNoCategoryTotalAmount(
        dateInterval: Pair<LocalDateTime, LocalDateTime>
    ): Pair<String, Double> {
        return Pair(
            Strings.OthersCategory.id,
            roomRepository.getTotalAmountWithoutCategory(dateInterval).first() ?: 0.0
        )
    }
}