package com.leoarmelin.meumercado.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.leoarmelin.meumercado.R
import com.leoarmelin.meumercado.extensions.toMoney
import com.leoarmelin.meumercado.ui.theme.MeuMercadoTheme
import com.leoarmelin.meumercado.ui.theme.Primary500
import com.leoarmelin.meumercado.ui.theme.Secondary800

@Composable
fun BottomCamera(totalPrice: String?, modifier: Modifier, onCameraClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp)
            .background(Primary500, RoundedCornerShape(4.dp, 4.dp, 0.dp, 0.dp))
            .then(modifier)
    ) {
        if (totalPrice != null) {
            Text(
                style = MaterialTheme.typography.h4,
                text = buildAnnotatedString {
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(stringResource(R.string.valor_total_dois_pontos))
                    }
                    append(" $totalPrice")
                },
                color = Color.White,
                modifier = Modifier
                    .padding(end = 20.dp)
                    .align(Alignment.CenterEnd)
            )
        }

        Button(
            onClick = onCameraClick,
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Secondary800
            ),
            modifier = Modifier
                .size(60.dp)
                .offset(x = 20.dp, y = (-12).dp)
                .shadow(3.dp, CircleShape)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_camera),
                contentDescription = stringResource(R.string.icone_de_camera_fotografica),
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    MeuMercadoTheme {
        BottomCamera(
            totalPrice = 22.0.toMoney(),
            modifier = Modifier
        ) {

        }
    }
}