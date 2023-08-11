package com.leoarmelin.meumercado.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.leoarmelin.meumercado.ui.theme.Black
import com.leoarmelin.meumercado.ui.theme.MeuMercadoTheme
import com.leoarmelin.meumercado.ui.theme.White

@ExperimentalPagerApi
@Composable
fun HomeTabs(state: PagerState, tabsList: List<String>, onSelectTab: (tabIndex: Int) -> Unit) {
    TabRow(
        selectedTabIndex = state.currentPage,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .shadow(6.dp),
        backgroundColor = White,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.pagerTabIndicatorOffset(state, tabPositions),
                color = White,
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
                        color = if (state.currentPage == index) Black else Black,
                        style = MaterialTheme.typography.h5
                    )
                },
                modifier = Modifier
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Preview
@Composable
private fun Preview() {
    MeuMercadoTheme {
        HomeTabs(state = rememberPagerState(), tabsList = listOf("Tab1", "Tab2"), onSelectTab = {})
    }
}