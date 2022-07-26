package com.leoarmelin.meumercado.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.leoarmelin.meumercado.R

// Set of Material typography styles to start with
val Metropolis = FontFamily(
    Font(R.font.metropolis_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.metropolis_regular, FontWeight.Normal, FontStyle.Normal),
    )

val Typography = Typography(
    h1 = TextStyle(
        fontFamily = Metropolis,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    h2 = TextStyle(
        fontFamily = Metropolis,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    h3 = TextStyle(
        fontFamily = Metropolis,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    h4 = TextStyle(
        fontFamily = Metropolis,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    ),
    h5 = TextStyle(
        fontFamily = Metropolis,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    body1 = TextStyle(
        fontFamily = Metropolis,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 16.8.sp,
        letterSpacing = 0.8.sp
    ),
    body2 = TextStyle(
        fontFamily = Metropolis,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = Metropolis,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
)