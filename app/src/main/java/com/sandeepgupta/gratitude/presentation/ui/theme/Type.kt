package com.sandeepgupta.gratitude.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.sandeepgupta.gratitude.R

val interFontFamily = FontFamily(
    Font(R.font.inter_black,FontWeight.Black),
    Font(R.font.inter_bold,FontWeight.Bold),
    Font(R.font.inter_extra_bold,FontWeight.ExtraBold),
    Font(R.font.inter_extra_light,FontWeight.ExtraLight),
    Font(R.font.inter_light,FontWeight.Light),
    Font(R.font.inter_medium,FontWeight.Medium),
    Font(R.font.inter_regular,FontWeight.Normal),
    Font(R.font.inter_semibold,FontWeight.SemiBold),
    Font(R.font.inter_thin,FontWeight.Thin),
)
// Set of Material typography styles to start with

val Typography = Typography(
    titleMedium = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontFamily = interFontFamily,
        fontWeight = FontWeight(600),
        ),
    titleSmall = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontFamily = interFontFamily,
        fontWeight = FontWeight(600),

        ),
    headlineMedium = TextStyle(
        fontSize = 22.sp,
        lineHeight = 28.sp,
        fontFamily = interFontFamily,
        fontWeight = FontWeight(400),
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontFamily = interFontFamily,
        fontWeight = FontWeight(400),
    ),
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontFamily = interFontFamily,
        fontWeight = FontWeight(400),
        ),
    labelLarge = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontFamily = interFontFamily,
        fontWeight = FontWeight(600),
    ),
    labelSmall = TextStyle(
        fontSize = 11.sp,
        lineHeight = 16.sp,
        fontFamily = interFontFamily,
        fontWeight = FontWeight(600),
    )
)