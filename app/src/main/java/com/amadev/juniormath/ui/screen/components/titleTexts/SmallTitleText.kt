package com.amadev.juniormath.ui.screen.components.titleTexts

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

@Composable
fun SmallTitleText(string : String) {
    Text(
        text = string,
        style = MaterialTheme.typography.body1,
        fontSize = 14.sp,
        color = MaterialTheme.colors.onSurface
    )
}