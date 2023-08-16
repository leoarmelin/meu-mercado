package com.leoarmelin.meumercado.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leoarmelin.meumercado.ui.theme.Black
import com.leoarmelin.meumercado.ui.theme.GrayOne
import com.leoarmelin.sharedmodels.Store

@Composable
fun StoreHeader(store: Store, onCloseTap: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .padding(horizontal = 40.dp)
                .align(Alignment.TopCenter)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = store.name,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = Black
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = store.address,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = Black
            )
        }

        Icon(
            modifier = Modifier.align(Alignment.TopEnd).size(24.dp).clickable { onCloseTap() },
            imageVector = Icons.Rounded.Close,
            contentDescription = "Fechar",
            tint = GrayOne
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun PreviewOne() {
    StoreHeader(
        store = Store(
            name = "Supermercado Leozinho",
            address = "Av. Duque de Caxias, 673"
        ),
        onCloseTap = {}
    )
}