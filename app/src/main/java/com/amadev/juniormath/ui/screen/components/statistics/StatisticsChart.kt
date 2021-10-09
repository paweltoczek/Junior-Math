package com.amadev.juniormath.ui.screen.components.statistics

import ChartBlockItem
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
                .height(164.dp)
                .clip(shape = RoundedCornerShape(15.dp))
                .background(MaterialTheme.colors.secondary),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ChartBlockItem( 15, dayName = "Mon")
            ChartBlockItem( (1..15).random(),dayName = "Tue")
            ChartBlockItem( (1..15).random(),dayName = "Wed")
            ChartBlockItem( (1..15).random(),dayName = "Thu")
            ChartBlockItem( (1..15).random(),dayName = "Fri")
            ChartBlockItem( (1..15).random(),dayName = "Sat")
            ChartBlockItem( (1..15).random(),dayName = "Sun")
        }
    }

}