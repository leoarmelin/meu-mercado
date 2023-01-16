package com.leoarmelin.meumercado.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.leoarmelin.meumercado.models.navigation.NavDestination
import com.leoarmelin.meumercado.ui.theme.Secondary50
import com.leoarmelin.meumercado.ui.theme.Secondary800
import com.leoarmelin.meumercado.viewmodels.MainViewModel
import com.leoarmelin.meumercado.viewmodels.NavigationViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navigationViewModel: NavigationViewModel, mainViewModel: MainViewModel) {
    val ticketsList = mainViewModel.fetchAllTickets().observeAsState(emptyList())

    LaunchedEffect(ticketsList.value) {
        delay(1000)

        mainViewModel.ticketsList.addAll(ticketsList.value)

        navigationViewModel.setRoute(
            if (ticketsList.value.isEmpty()) NavDestination.Start.routeName
            else NavDestination.Home.routeName
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Secondary50)
    ) {
        Text(
            text = stringResource(com.leoarmelin.meumercado.R.string.nfc_e_scanner),
            style = MaterialTheme.typography.h1,
            fontSize = 40.sp,
            textAlign = TextAlign.Center,
            color = Secondary800,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}