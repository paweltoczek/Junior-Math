package com.amadev.juniormath.ui.screen.components.statistics

import CustomComponent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amadev.juniormath.ui.theme.JuniorMathTheme

@Preview
@Composable
fun StatisticsChart() {
    JuniorMathTheme {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(shape = RoundedCornerShape(15.dp))
                .background(MaterialTheme.colors.secondary)
                .absolutePadding(0.dp, 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomComponent((0..99).random())
            CustomComponent((0..99).random())
            CustomComponent((0..99).random())
            CustomComponent((0..99).random())
            CustomComponent((0..99).random())
            CustomComponent((0..99).random())
            CustomComponent((0..99).random())
        }
    }
}