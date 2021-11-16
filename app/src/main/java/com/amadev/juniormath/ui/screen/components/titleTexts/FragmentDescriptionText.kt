package com.amadev.juniormath.ui.screen.components.titleTexts

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun FragmentDescriptionText(string : String, fontSize : TextUnit = 16.sp) {
    Text(
        text = string,
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = fontSize,
        color = MaterialTheme.colors.primary
    )
}