package com.leoarmelin.meumercado.ui.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.leoarmelin.meumercado.ui.theme.CreamOne
import com.leoarmelin.meumercado.ui.theme.MeuMercadoTheme
import com.leoarmelin.meumercado.ui.theme.White

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ScreenBottomSheet(
    content: @Composable (PaddingValues) -> Unit,
    sheetContent: @Composable ColumnScope.() -> Unit
) {
    val sheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Expanded)
    )
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val halfScreenHeight = remember { (screenHeight / 2f).dp }

    BottomSheetScaffold(
        scaffoldState = sheetState,
        sheetShape = RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp),
        sheetContent = sheetContent,
        sheetPeekHeight = halfScreenHeight,
        sheetBackgroundColor = CreamOne,
        backgroundColor = White,
        content = content
    )
}

@Preview(
    showBackground = true,
    device = Devices.NEXUS_5
)
@Composable
private fun PreviewOne() {
    MeuMercadoTheme {
        ScreenBottomSheet(
            content = {
                Text(text = "Content")
            },
            sheetContent = {
                Text(text = "Sheet Content")
            }
        )
    }
}