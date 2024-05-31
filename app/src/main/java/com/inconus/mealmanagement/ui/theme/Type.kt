package com.inconus.mealmanagement.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.inconus.mealmanagement.R

val pretendard = FontFamily(
    Font(R.font.pretendard_bold,FontWeight.Bold, FontStyle.Normal),
    Font(R.font.pretendard_semi_bold,FontWeight.SemiBold, FontStyle.Normal),
    Font(R.font.pretendard_medium,FontWeight.Medium, FontStyle.Normal),
    Font(R.font.pretendard_regular,FontWeight.Normal, FontStyle.Normal), //reqular
)

// Set of Material typography styles to start with
val MainTypography = Typography(
    bodyMedium = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 20.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
    // 버튼
    labelMedium = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 20.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 20.sp,
    )

    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)