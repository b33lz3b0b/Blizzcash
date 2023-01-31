package com.example.blizzcash.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.blizzcash.R


val Firamono = FontFamily(
    Font(R.font.firamono_regular),
    Font(R.font.firamono_bold, FontWeight.Bold),
    Font(R.font.firamono_medium, FontWeight.SemiBold)
)

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = Firamono,
        fontWeight = FontWeight.SemiBold,
        fontSize = 40. sp,
        lineHeight = 38. sp
    ),
    displayMedium = TextStyle(
        fontFamily = Firamono,
        fontWeight = FontWeight.SemiBold,
        fontSize = 35. sp,
        lineHeight = 33. sp
    ),
    displaySmall = TextStyle(
        fontFamily = Firamono,
        fontWeight = FontWeight.SemiBold,
        fontSize = 30. sp,
        lineHeight = 28. sp
    ),
    headlineLarge= TextStyle(
        fontFamily = Firamono,
        fontWeight = FontWeight.Bold,
        fontSize = 38. sp,
        lineHeight = 38. sp
    ),
    headlineMedium = TextStyle(
        fontFamily = Firamono,
        fontWeight = FontWeight.Bold,
        fontSize = 35. sp,
        lineHeight = 35. sp
    ),
    headlineSmall = TextStyle(
        fontFamily = Firamono,
        fontWeight = FontWeight.Bold,
        fontSize = 32. sp,
        lineHeight = 32. sp
    ),
    titleLarge = TextStyle(
        fontFamily = Firamono,
        fontWeight = FontWeight.SemiBold,
        fontSize = 30. sp,
        lineHeight = 28. sp
    ),
    titleMedium = TextStyle(
        fontFamily = Firamono,
        fontWeight = FontWeight.SemiBold,
        fontSize = 27. sp,
        lineHeight = 25. sp
    ),
    titleSmall = TextStyle(
        fontFamily = Firamono,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24. sp,
        lineHeight = 22. sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Firamono,
        fontSize = 21. sp,
        lineHeight = 19. sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Firamono,
        fontSize = 18. sp,
        lineHeight = 16. sp
    ),
    bodySmall= TextStyle(
        fontFamily = Firamono,
        fontSize = 15. sp,
        lineHeight = 12. sp
    ),
    labelLarge = TextStyle(
        fontFamily = Firamono,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12. sp,
        lineHeight = 10. sp
    ),
    labelMedium = TextStyle(
        fontFamily = Firamono,
        fontWeight = FontWeight.SemiBold,
        fontSize = 10. sp,
        lineHeight = 8. sp
    ),
    labelSmall = TextStyle(
        fontFamily = Firamono,
        fontWeight = FontWeight.SemiBold,
        fontSize = 8. sp,
        lineHeight = 6. sp
    )
)