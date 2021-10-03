package com.amadev.juniormath.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.amadev.juniormath.R

val OpenSansBold =
    FontFamily(Font(R.font.open_sans_bold), Font(R.font.open_sans_bold, FontWeight.Bold))

val OpenSansRegular = FontFamily(Font(R.font.open_sans_regular))

val OpenSansLight = FontFamily(Font(R.font.open_sans_light))

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = OpenSansBold,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    ),

    body2 = TextStyle(
        fontFamily = OpenSansRegular,
        fontSize = 24.sp
    ),

    h1 = TextStyle(
        fontFamily = OpenSansBold,
        fontSize = 16.sp
    ),

    h2 = TextStyle(
        fontFamily = OpenSansRegular,
        fontSize = 16.sp
    ),

    h3 = TextStyle(
        fontFamily = OpenSansLight,
        fontSize = 16.sp
    ),

    h4 = TextStyle(
        fontFamily = OpenSansLight,
        fontSize = 12.sp
    )


    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)