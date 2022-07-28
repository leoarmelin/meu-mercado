package com.leoarmelin.meumercado.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.leoarmelin.meumercado.ui.theme.Gray400
import com.leoarmelin.meumercado.ui.theme.Secondary50
import com.leoarmelin.meumercado.ui.theme.Secondary800

@ExperimentalPagerApi
@Composable
fun HomeTabs(state: PagerState, tabsList: List<String>, onSelectTab: (tabIndex: Int) -> Unit) {
    TabRow(
        selectedTabIndex = state.currentPage,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .shadow(6.dp),
        backgroundColor = Secondary50,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.pagerTabIndicatorOffset(state, tabPositions),
                color = Secondary800,
                height = TabRowDefaults.IndicatorHeight * 1.5F
            )
        }
    ) {
        tabsList.forEachIndexed { index, title ->
            Tab(
                selected = state.currentPage == index,
                onClick = { onSelectTab(index) },
                text = {
                    Text(
                        title,
                        color = if (state.currentPage == index) Secondary800 else Gray400,
                        style = MaterialTheme.typography.h5
                    )
                },
                modifier = Modifier
            )
        }
    }
}