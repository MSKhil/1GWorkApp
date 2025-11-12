package com.example.a1gworkapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.a1gworkapp.R

val NewOrder = FontFamily(
    Font(R.font.new_order, FontWeight.SemiBold)
)

val MidpointPro = FontFamily(
    Font(R.font.midpoint_pro, FontWeight.Normal)
)

val Typography = Typography(
    //Стиль для 1Galaxy
    displayLarge = TextStyle(
        fontFamily = NewOrder,
        fontWeight = FontWeight.SemiBold,
        fontSize = 48.sp
    ),
    //Стиль для текста в кнопках и полях ввода
    labelLarge = TextStyle(
        fontFamily = MidpointPro,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    // Для больших заголовков, как "ХИЛЬ
    headlineLarge = TextStyle(
        fontFamily = NewOrder,
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp,
        lineHeight = 48.sp,
        letterSpacing = 0.sp
    ),
    // Для подзаголовков, как "Текущая неделя"
    titleLarge = TextStyle(
        fontFamily = MidpointPro,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    // Для основного текста в карточках
    bodyLarge = TextStyle(
        fontFamily = MidpointPro,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
    // Для мелкого текста, как "Месяц: Ноябрь"
    labelMedium = TextStyle(
        fontFamily = MidpointPro,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.5.sp
    )
)