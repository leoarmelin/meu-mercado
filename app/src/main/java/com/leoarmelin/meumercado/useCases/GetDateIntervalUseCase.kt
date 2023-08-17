package com.leoarmelin.meumercado.useCases

import java.time.LocalDateTime
import javax.inject.Inject

class GetDateIntervalUseCase @Inject constructor() {
    fun initialInterval(): Pair<LocalDateTime, LocalDateTime> {
        val now = LocalDateTime.now()
        return interval(now.monthValue, now.year)
    }

    fun interval(month: Int, year: Int): Pair<LocalDateTime, LocalDateTime> {
        val startDate = startDate(month, year)
        val endDate = endDate(startDate)
        return Pair(startDate, endDate)
    }

    private fun startDate(month: Int, year: Int) =
        LocalDateTime.of(year, month, 1, 0, 0, 1)

    private fun endDate(date: LocalDateTime) = date.plusMonths(1).minusSeconds(2)
}