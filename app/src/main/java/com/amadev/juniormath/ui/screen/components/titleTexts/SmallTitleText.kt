package com.amadev.juniormath.ui.screen.components.titleTexts

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun SmallTitleText(string : String, fontSize : TextUnit = 14.sp) {
    Text(
        text = string,
        style = MaterialTheme.typography.body1,
        fontSize = fontSize,
        color = MaterialTheme.colors.onSurface
    )
}