package com.amadev.juniormath.ui.screen.components.errorText

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun ErrorText(errorText: String) {
    Text(
        text = errorText,
        color = Color.Red,
        style = MaterialTheme.typography.subtitle1,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold
    )
}