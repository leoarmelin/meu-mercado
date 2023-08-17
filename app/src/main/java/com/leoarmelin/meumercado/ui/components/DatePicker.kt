package com.leoarmelin.meumercado.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.leoarmelin.meumercado.ui.theme.Black
import com.leoarmelin.meumercado.ui.theme.Blue
import com.leoarmelin.meumercado.ui.theme.Pink
import com.leoarmelin.meumercado.ui.theme.White
import java.time.LocalDateTime

@Composable
fun DatePicker(
    modifier: Modifier,
    isOpen: Boolean,
    dateInterval: Pair<LocalDateTime, LocalDateTime>,
    onApply: (month: Int, year: Int) -> Unit,
    onClose: () -> Unit
) {
    var selectedMonth by remember(dateInterval.first) {
        mutableStateOf(dateInterval.first.month.value)
    }
    var selectedYear by remember(dateInterval.first) {
        mutableStateOf(dateInterval.first.year)
    }

    EnterExitAnimation(isOpen) {
        Row(
            modifier = modifier.height(60.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .shadow(5.dp)
                    .background(White, RoundedCornerShape(8.dp))
            ) {
                ItemColumn(
                    modifier = Modifier.width(50.dp),
                    items = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
                    selectedValue = selectedMonth,
                    onSelect = { selectedMonth = it }
                )

                ItemColumn(
                    modifier = Modifier.width(70.dp),
                    items = listOf(2018, 2019, 2020, 2021, 2022, 2023, 2024, 2025, 2026, 2027),
                    selectedValue = selectedYear,
                    onSelect = { selectedYear = it }
                )
            }

            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Close",
                    tint = White,
                    modifier = Modifier
                        .width(24.dp)
                        .height(24.dp)
                        .background(Pink, CircleShape)
                        .clickable { onClose() }
                        .padding(2.dp)
                )

                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Choose",
                    tint = White,
                    modifier = Modifier
                        .width(24.dp)
                        .height(24.dp)
                        .background(Blue, CircleShape)
                        .clickable { onApply(selectedMonth, selectedYear) }
                        .padding(2.dp)
                )
            }
        }
    }
}

@Composable
fun EnterExitAnimation(isOpen: Boolean, content: @Composable () -> Unit) {
    AnimatedVisibility(
        visible = isOpen,
        enter = slideInHorizontally(spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessMediumLow)) { -400 },
        exit = slideOutHorizontally(spring()) { -500 }
    ) {
        content()
    }
}

@Composable
private fun ItemColumn(modifier: Modifier, items: List<Int>, selectedValue: Int, onSelect: (Int) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxHeight()
    ) {
        items(items) {
            val stringValue = remember(it) { getMonthName(it) }
            Text(
                text = stringValue,
                textAlign = TextAlign.Center,
                color = if (selectedValue == it) Pink else Black,
                modifier = modifier
                    .height(20.dp)
                    .clickable { onSelect(it) }
            )
        }
    }
}

private fun getMonthName(month: Int): String = when (month) {
    1 -> "Jan"
    2 -> "Fev"
    3 -> "Mar"
    4 -> "Abr"
    5 -> "Mai"
    6 -> "Jun"
    7 -> "Jul"
    8 -> "Ago"
    9 -> "Set"
    10 -> "Out"
    11 -> "Nov"
    12 -> "Dez"
    else -> month.toString()
}