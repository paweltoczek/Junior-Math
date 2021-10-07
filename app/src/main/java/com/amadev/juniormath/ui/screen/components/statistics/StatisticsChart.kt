package com.amadev.juniormath.ui.screen.components.statistics

import ChartBlockItem
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            ChartBlockWithDayName("Mon", (0..80).random())
            ChartBlockWithDayName("Tue", (0..80).random())
            ChartBlockWithDayName("Wed", (0..80).random())
            ChartBlockWithDayName("Thu", (0..80).random())
            ChartBlockWithDayName("Fri", (0..80).random())
            ChartBlockWithDayName("Sat", (0..80).random())
            ChartBlockWithDayName("Sun", (0..80).random())
        }
    }

}

@Composable
fun ChartBlockWithDayName(day: String, blockValue: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        ChartBlockItem(blockValue)
        Text(
            text = day,
            fontSize = 12.sp,
            modifier = Modifier.absolutePadding(0.dp, 10.dp),
            color = MaterialTheme.colors.onSurface
        )
    }
}